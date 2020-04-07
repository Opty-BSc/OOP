package sth.core;

import sth.core.exception.*;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;

class Discipline implements java.io.Serializable, Observable {

    private static final long serialVersionUID = 346237327422L;

    private String _name;
    private Course _course;
    private int _capacity;
    private Map<Integer, Student> _students;
    private Map<Integer, Teacher> _teachers;
    private Map<String, Project> _projects;
    private List<Observer> _observers;

    Discipline(String name, Course course, int capacity) {
        _name = name;
        _course = course;
        _capacity = capacity;
        _students = new HashMap<Integer, Student>();
        _teachers = new HashMap<Integer, Teacher>();
        _projects = new HashMap<String, Project>();
        _observers = new ArrayList<Observer>();
    }

    Discipline(String name, Course course) {
        this(name, course, 200);
    }

    boolean enrollStudent(Student student) {

        if (student != null && _students.size() < _capacity && !_students.containsValue(student)) {
            _students.put(student.getId(), student);
            return true;
        }
        return false;
    }

    boolean addTeacher(Teacher teacher) {

        if (teacher != null && !_teachers.containsValue(teacher)) {
            _teachers.put(teacher.getId(), teacher);
            return true;
        }
        return false;
    }

    String getName() {
        return _name;
    }

    Course getCourse() {
        return _course;
    }

    Collection<Student> getStudents() {
        return new ArrayList<Student>(_students.values());
    }

    Collection<Survey> getSurveys() throws NoSurveyStateException {

        List<Survey> surveys = new ArrayList<Survey>();

        for (Project p : _projects.values()) {
            if (p.hasSurvey()) {
                surveys.add(p.getSurvey());
            }
        }
        return surveys;
    }

    void addProject(String projectName) throws DuplicateProjectIdException {
        if (!_projects.containsKey(projectName)) {
            Project project = new Project(this, projectName);
            _projects.put(projectName, project);
        } else {
            throw new DuplicateProjectIdException(projectName);
        }
    }

    void closeProject(String projectName) throws NoSuchProjectIdException, SurveyStateException {
        if (_projects.containsKey(projectName)) {
            _projects.get(projectName).closeProject();
        } else {
            throw new NoSuchProjectIdException(projectName);
        }
    }

    Project getProject(String projectName) throws NoSuchProjectIdException {
        Project project = _projects.get(projectName);

        if (project == null) {
            throw new NoSuchProjectIdException(projectName);
        }
        return project;
    }

    @Override
    public void sendNotifications(Notification notification) {
        for (Observer o : _observers) {
            o.update(notification);
        }
    }

    @Override
    public void subscribe(Observer observer) {
        if (!_observers.contains(observer)) {
            _observers.add(observer);
        }
    }

    @Override
    public void unsubscribe(Observer observer) {
        for (Observer o : _observers) {
            if (observer.equals(o)) {
                _observers.remove(o);
                return;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Discipline) {
            Discipline discipline = (Discipline)obj;
            return  _name.equals(discipline.getName()) && _course.equals(discipline.getCourse());
        }
        return false;
    }

    @Override
    public String toString() {
        return "* " + _course.toString() + " - " + _name + "\n";
    }

    void removeStudent(Integer id) throws NoSuchDisciplineIdException, NoSuchPersonIdException {

        if (!_students.containsKey(id)) {
            throw new NoSuchPersonIdException(id);
        }

        Student student = _students.get(id);
        student.removeDiscpline(this);

        _students.remove(id);
    }

    String projectsSubmissions(Student student) {

        String result = "";

        for (Project proj : _projects.values()) {
            if (proj.hasSubmited(student)) {
                result = result.concat(_name + " - " + proj.getName() + "\n");
            }
        }

        return result;
    }
}
