package sth.core;

import sth.core.exception.BadEntryException;
import sth.core.exception.NoSuchDisciplineIdException;


class Student extends Notifiable implements java.io.Serializable {

    private static final long serialVersionUID = 237642374298L;

    private Course _course;
    private boolean _isRepresentative;

    Student(Integer id, int phoneNumber, String name, boolean isRepresentative) {
        super(id, phoneNumber, name);
        _course = null;
        _isRepresentative = isRepresentative;
    }

    Course getCourse() {
        return _course;
    }

    @Override
    void parseContext(String lineContext, School school) throws BadEntryException {
        String components[] =  lineContext.split("\\|");

        if (components.length != 2)
            throw new BadEntryException("Invalid line context " + lineContext);

        if (_course == null) {
            _course = school.parseCourse(components[0]);
            _course.addStudent(this);

            if (_isRepresentative) {
                _course.addRepresentative(this);
            }
        }

        Discipline dis = _course.parseDiscipline(components[1]);
        dis.enrollStudent(this);
        dis.subscribe(this);
        addDiscipline(dis);
    }

    boolean isRepresentative() {
        return _isRepresentative;
    }

    void setRepresentative(boolean isRepresentative){
        _isRepresentative = isRepresentative;

        if (_isRepresentative) {
            _course.addRepresentative(this);
        } else {
            _course.removeRepresentative(this);
        }
    }

    Discipline getRepresentativeDiscipline(String disciplineName) throws NoSuchDisciplineIdException {

        if(_isRepresentative) {
            for (Discipline d : _course.getDisciplines()) {
                if (disciplineName.equals(d.getName())) {
                    return d;
                }
            }
        }
        throw new NoSuchDisciplineIdException(disciplineName);
    }

    @Override
    String getType() {
        return _isRepresentative? "DELEGADO":"ALUNO";
    }

    @Override
    String getInfo() {
        return super.getInfo();
    }

    @Override
    String resultsFormat(Survey survey) {

        Project project = survey.getProject();
        Discipline discipline = project.getDiscipline();

        String result = discipline.getName() + " - " + project.getName() + "\n";

        result = result.concat(" * Número de respostas: " + survey.getNumAnswers() + "\n");
        result = result.concat(" * Tempo médio (horas): " + survey.getAverageHours() + "\n");

        return result;
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
