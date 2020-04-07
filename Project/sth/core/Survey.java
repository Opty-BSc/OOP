package sth.core;

import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSurveyStateException;
import sth.core.exception.SurveyStateException;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

class Survey implements  java.io.Serializable, Comparable<Survey> {

    private static final long serialVersionUID = 3843478263437L;

    class Answer implements java.io.Serializable {

        private static final long serialVersionUID = 876545455476L;

        private int _hours;
        private String _submission;

        Answer(int hours, String submission) {
            _hours = hours;
            _submission = submission;
        }

        public int getHours() {
            return _hours;
        }

        public String getSubmission() {
            return _submission;
        }
    }

    private Project _project;
    private SurveyState _state;
    private Set<Student> _subStudents;
    private List<Answer> _studentsAnswers;
    private int _maxHours;
    private int _minHours;
    private int _averageHours;

    Survey(Project project) {
        _project = project;
        _state = new SurveyCreated();
        _subStudents = new HashSet<Student>();
        _studentsAnswers = new ArrayList<Answer>();
        _minHours = 0;
        _maxHours = 0;
        _averageHours = 0;
    }

    Project getProject() {
        return _project;
    }

    void setState(SurveyState state) {
        _state = state;
    }

    void cancel() throws SurveyStateException {
        _state.cancel(this);
    }

    void open() throws SurveyStateException {
        _state.open(this);
    }

    void close() throws SurveyStateException {
        _state.close(this);
    }

    void finish() throws SurveyStateException {
        _state.finish(this);
    }

    void registAnswer(Student student, int hours, String comment)
            throws SurveyStateException, NoSuchProjectIdException {

        if (!_project.hasSubmited(student)) {
            throw new NoSuchProjectIdException(_project.getName());
        }

        if (comment.length() < 10) {
            throw new NoSurveyStateException();
        }

        _state.registerAnswer(this, student, new Answer(hours, comment));
    }

    void addAnswer(Student student, Answer answer) {

        if (!_subStudents.contains(student)) {
            _subStudents.add(student);
            _studentsAnswers.add(answer);
        }
    }

    boolean hasAnswers() {
        return _subStudents.size() > 0;
    }

    int getNumAnswers() {
        return _studentsAnswers.size();
    }

    String showResults(Notifiable notifiable) {
        return _state.showResults(notifiable, this);
    }

    String getOpenNotification(String projectName, String disciplineName) {

        return "Pode preencher inquérito do projecto " + projectName +
                " da disciplina " + disciplineName + "\n";
    }

    String getFinishNotification(String projectName, String disciplineName) {

        return "Resultados do inquérito do projecto " + projectName +
                " da disciplina " + disciplineName + "\n";
    }

    void calcHours() {

        if (_studentsAnswers.size() == 0) {
            return;
        }

        int hours = _studentsAnswers.get(0).getHours();
        _minHours = hours;
        _maxHours = hours;

        int totalHours = 0;

        for (Answer a : _studentsAnswers) {
            hours = a.getHours();
            if (hours < _minHours) {
                _minHours = hours;
            }
            if (hours > _maxHours) {
                _maxHours = hours;
            }
            totalHours += hours;
        }

        _averageHours = totalHours / _studentsAnswers.size();
    }

    int getMinHours() {
        return _minHours;
    }

    int getMaxHours() {
        return _maxHours;
    }

    int getAverageHours() {
        return _averageHours;
    }

    @Override
    public int compareTo(Survey o) {
        return _project.compareTo(o.getProject());
    }
}
