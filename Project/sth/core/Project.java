package sth.core;

import sth.core.exception.DuplicateSurveyStateException;
import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSurveyStateException;
import sth.core.exception.SurveyStateException;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

class Project implements java.io.Serializable, Comparable<Project> {

    private static final long serialVersionUID = 876585345876L;

    private Discipline _discipline;
    private String _name;
    private String _descrition;
    private boolean _open;
    private Survey _survey;
    private Map<Student, String> _submissions;

    Project(Discipline discipline, String name, String description){
        _discipline = discipline;
        _name = name;
        _descrition = description;
        _open = true;
        _survey = null;
        _submissions = new HashMap<Student, String >();
    }

    Project(Discipline discipline, String name) {
       this(discipline, name,null);
    }

    Discipline getDiscipline() {
        return _discipline;
    }

    String getName() {
        return _name;
    }

    boolean isOpen() { return _open; }

    boolean addSubmission(Student student, String submission) throws NoSuchProjectIdException {
        if (!_open){
            throw new NoSuchProjectIdException(_name);
        }
        return _submissions.put(student, submission) != null;
    }

    int getNumSubmissions() {
        return _submissions.size();
    }

    String showSubmissions(){

        List<Student> submitter  = new ArrayList<Student>(_submissions.keySet());
        Collections.sort(submitter );

        String result = _discipline.getName() + " - " + _name + "\n";
        for (Student s : submitter ){
            result =  result.concat("* " + s.getId() + " - " + _submissions.get(s) + "\n");
        }
        return result;
    }

    void closeProject() throws SurveyStateException {
        _open = false;
        if(_survey != null){
            _survey.open();
        }
    }

    void addSurvey() throws DuplicateSurveyStateException, NoSuchProjectIdException {
        if (!_open) {
            throw new NoSuchProjectIdException(_name);
        }

        if (_survey != null) {
            throw new DuplicateSurveyStateException();
        }
        _survey = new Survey(this);
    }

    boolean hasSurvey() {
        return _survey != null;
    }

    Survey getSurvey() throws NoSurveyStateException {
        if (_survey == null){
            throw new NoSurveyStateException();
        }
        return _survey;
    }

    void removeSurvey() {
        _survey = null;
    }

    @Override
    public int compareTo(Project o) {
        return _name.compareTo(o.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Project) {
            Project project = (Project)obj;
            return _name.equals(project.getName());
        }
        return false;
    }

    boolean hasSubmited(Student student) {

        return _submissions.containsKey(student);
    }
}
