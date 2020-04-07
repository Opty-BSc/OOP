package sth.core;

import sth.core.exception.BadEntryException;

import java.util.List;
import java.util.ArrayList;

class Teacher extends Notifiable implements java.io.Serializable {

    private static final long serialVersionUID = 786567675567L;

    private List<Discipline> _disciplines;

    Teacher(Integer id, int phoneNumber, String name) {
        super(id, phoneNumber, name);
        _disciplines = new ArrayList<Discipline>();
    }

    @Override
    void parseContext(String lineContext, School school) throws BadEntryException {
        String components[] =  lineContext.split("\\|");

        if (components.length != 2)
            throw new BadEntryException("Invalid line context " + lineContext);

        Course course = school.parseCourse(components[0]);
        Discipline discipline = course.parseDiscipline(components[1]);

        discipline.addTeacher(this);
        discipline.subscribe(this);
        addDiscipline(discipline);
    }

    @Override
    String getType() {
        return "DOCENTE";
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

        result = result.concat(" * Número de submissões: " + project.getNumSubmissions() + "\n");
        result = result.concat(" * Número de respostas: " + survey.getNumAnswers() + "\n");
        result = result.concat(" * Tempos de resolução (horas) (mínimo, médio, máximo): ");
        result = result.concat(survey.getMinHours() + ", " + survey.getAverageHours() + ", " + survey.getMaxHours() + "\n");

        return result;
    }
}
