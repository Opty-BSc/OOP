package sth.core;

import sth.core.exception.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * The façade class.
 */
public class SchoolManager {

    private School _school;
    private Person _user;
    private String _filename;

    public SchoolManager(){
        _school = new School();
        _filename = null;
        _user = null;
    }

    /**
    * @param datafile
    * @throws ImportFileException
    */
    public void importFile(String datafile) throws ImportFileException {
        try {
            _school.importFile(datafile);
        } catch (IOException | BadEntryException e) {
            throw new ImportFileException(e);
        }
    }

    /**
    * Do the login of the user with the given identifier.

    * @param id identifier of the user to login
    * @throws NoSuchPersonIdException if there is no user with the given identifier
    */
    public void login(int id) throws NoSuchPersonIdException {
        _user = _school.getPersonById(id);
    }

    /**
    * @return true when the currently logged in person is an administrative
    */
    public boolean isLoggedUserAdministrative() {
        return _user instanceof Employee;
    }

    /**
    * @return true when the currently logged in person is a professor
    */
    public boolean isLoggedUserProfessor() {
        return _user instanceof Teacher;
    }

    /**
    * @return true when the currently logged in person is a student
    */
    public boolean isLoggedUserStudent() {
        return _user instanceof Student;
    }

    /**
    * @return true when the currently logged in person is a representative
    */
    public boolean isLoggedUserRepresentative() {
        return _user instanceof Student && ((Student)_user).isRepresentative();
    }

    public Person getLoggedUser() {
        return _user;
    }

    public String load(String filename) throws NoSuchPersonIdException, IOException, ClassNotFoundException {

        FileInputStream fpin = new FileInputStream(filename);
        ObjectInputStream obIn = new ObjectInputStream(fpin);
        Object anObject = obIn.readObject();
        obIn.close();

        School school = (School)anObject;

        if (_user != null && school.getPersonById(_user.getId()) != null) {
            _school = school;
            _user = school.getPersonById(_user.getId());
            _filename = filename;
        }

        return getNotifications();
    }

    public String getFilename() {
        return _filename;
    }

    private String getNotifications(){

        if(isLoggedUserStudent() || isLoggedUserProfessor()){
            Notifiable notifiable = (Notifiable)_user;
            String notifications = "";

            for (Notification n : notifiable.getNotifications()){
                notifications = notifications.concat(n.toString());
            }
            return notifications;
        }
        return null;
    }

    public void save(String filename) throws IOException {

        FileOutputStream fpout = new FileOutputStream(filename);
        ObjectOutputStream obOut = new ObjectOutputStream(fpout);
        obOut.writeObject(_school);
        obOut.close();

        if (_filename == null) {
            _filename = filename;
        }
    }

    public String showPerson() {
        if (_user != null) {
            return _user.toString();
        }
        return null;
    }

    public String showAllPersons() {
        String personsToString = "";

        List<Person> persons = (List<Person>)_school.getAllUsers();
        Collections.sort(persons);

        for (Person p : persons) {
            personsToString = personsToString.concat(p.toString());
        }
        return personsToString;
    }

    public void changePhoneNumber(int phoneNumber) {
        if (_user != null) {
            _user.setPhoneNumber(phoneNumber);
        }
    }

    public String searchPerson(String name) {

        List<Person> persons = new ArrayList<Person>();
        for (Person p: _school.getAllUsers()) {
            if (p.getName().contains(name)) {
                persons.add(p);
            }
        }
        persons.sort(_user);

        String personsToString = "";
        for (Person p: persons) {
            personsToString = personsToString.concat(p.toString());
        }
        return personsToString;
    }

    public void createProject(String disciplineName, String projectName)
            throws NoSuchDisciplineIdException, DuplicateProjectIdException {

        if (isLoggedUserProfessor()) {
            Teacher teacher = (Teacher)_user;
            teacher.getDiscipline(disciplineName).addProject(projectName);
        }
    }

    public void closeProject(String disciplineName, String projectName)
            throws NoSuchDisciplineIdException, NoSuchProjectIdException, SurveyStateException {

        if (isLoggedUserProfessor()) {
            Teacher teacher = (Teacher)_user;
            teacher.getDiscipline(disciplineName).closeProject(projectName);
        }
    }

    public String showProjectSubmissions(String disciplineName, String projectName)
            throws NoSuchDisciplineIdException, NoSuchProjectIdException {

        if (isLoggedUserProfessor()) {
            Teacher teacher = (Teacher) _user;
            return teacher.getDiscipline(disciplineName).getProject(projectName).showSubmissions();
        }
        return null;
    }

    public String showDisciplineStudents(String disciplineName) throws NoSuchDisciplineIdException {

        if (isLoggedUserProfessor()) {
            Teacher teacher = (Teacher)_user;
            String studentsToString = "";

            List<Student> students = (List<Student>)teacher.getDiscipline(disciplineName).getStudents();
            Collections.sort(students);

            for (Student s : students) {
                studentsToString = studentsToString.concat(s.toString());
            }
            return studentsToString;
        }
        return null;
    }

    public String showSurveyResults(String disciplineName, String projectName)
            throws NoSuchDisciplineIdException, NoSuchProjectIdException, SurveyStateException {

        if (isLoggedUserStudent() || isLoggedUserProfessor()) {
            Notifiable notifiable = (Notifiable) _user;
            return notifiable.getDiscipline(disciplineName).getProject(projectName).getSurvey().showResults(notifiable);
        }
        return null;
    }

    public void submitProject(String disciplineName, String projectName, String submission)
            throws NoSuchDisciplineIdException, NoSuchProjectIdException {

        if (isLoggedUserStudent()){
            Student student = (Student)_user;
            student.getDiscipline(disciplineName).getProject(projectName).addSubmission(student, submission);
        }
    }

    public void answerSurvey(String disciplineName, String projectName, int hours, String comment)
            throws NoSuchDisciplineIdException, NoSuchProjectIdException, SurveyStateException {

        if (isLoggedUserStudent()) {
            Student student = (Student) _user;
            student.getDiscipline(disciplineName).getProject(projectName).getSurvey().registAnswer(student, hours, comment);
        }
    }

    public void createSurvey(String disciplineName, String projectName)
            throws NoSuchDisciplineIdException, SurveyStateException, NoSuchProjectIdException {

        if (isLoggedUserRepresentative()) {
            Project project = ((Student)_user).getRepresentativeDiscipline(disciplineName).getProject(projectName);
            project.addSurvey();
        }
    }

    public void cancelSurvey(String disciplineName, String projectName)
            throws NoSuchDisciplineIdException, SurveyStateException, NoSuchProjectIdException {

        if (isLoggedUserRepresentative()) {
            Survey survey = ((Student) _user).getRepresentativeDiscipline(disciplineName).getProject(projectName).getSurvey();
            survey.cancel();
        }
    }

    public void openSurvey(String disciplineName, String projectName)
            throws NoSuchDisciplineIdException, SurveyStateException, NoSuchProjectIdException {

        if (isLoggedUserRepresentative()) {
            Survey survey = ((Student) _user).getRepresentativeDiscipline(disciplineName).getProject(projectName).getSurvey();
            survey.open();
        }
    }

    public void closeSurvey(String disciplineName, String projectName)
            throws NoSuchDisciplineIdException, SurveyStateException, NoSuchProjectIdException {

        if (isLoggedUserRepresentative()) {
            Survey survey = ((Student) _user).getRepresentativeDiscipline(disciplineName).getProject(projectName).getSurvey();
            survey.close();
        }
    }

    public void finishSurvey(String disciplineName, String projectName)
            throws NoSuchDisciplineIdException, SurveyStateException, NoSuchProjectIdException {

        if (isLoggedUserRepresentative()) {
            Survey survey = ((Student) _user).getRepresentativeDiscipline(disciplineName).getProject(projectName).getSurvey();
            survey.finish();
        }
    }

    public String showDisciplineSurveys(String disciplineName)
            throws NoSuchDisciplineIdException, NoSurveyStateException {

        if (isLoggedUserRepresentative()) {
            Discipline discipline = ((Student) _user).getRepresentativeDiscipline(disciplineName);
            List<Survey> surveys = new ArrayList<Survey>(discipline.getSurveys());
            Collections.sort(surveys);

            String results = "";
            for (Survey s : surveys) {
                results = results.concat(s.showResults(null));
            }
            return results;
        }
        return null;
    }

    public void removeStudent(String disciplineName, Integer id) throws NoSuchDisciplineIdException, NoSuchPersonIdException {

        if (isLoggedUserProfessor()) {

            Teacher teacher = (Teacher)_user;
            Discipline dis = teacher.getDiscipline(disciplineName);
            dis.removeStudent(id);
        }
    }

    public String showSubmissions() {

        if (isLoggedUserStudent()) {

            Student student = (Student)_user;

            String result = "";

            for (Discipline dis : student.getDisciplines()) {
                result = result.concat(dis.projectsSubmissions(student));
            }

            return result;
        }

        return null;
    }
}
