package sth.core;

import sth.core.exception.FinishedSurveyStateException;
import sth.core.exception.NoSurveyStateException;
import sth.core.exception.SurveyStateException;
import sth.core.exception.OpeningSurveyStateException;
import sth.core.exception.ClosingSurveyStateException;

class SurveyFinalized implements SurveyState {

    private static final long serialVersionUID = 954854347834L;

    private int _minHours = -1;

    public void cancel(Survey survey) throws SurveyStateException {
        throw new FinishedSurveyStateException();
    }

    public void open(Survey survey) throws SurveyStateException {
        throw new OpeningSurveyStateException();
    }

    public void close(Survey survey) throws SurveyStateException {
        throw new ClosingSurveyStateException();
    }

    public void finish(Survey survey) {}

    public void registerAnswer(Survey survey, Student student, Survey.Answer answer) throws SurveyStateException {
        throw new NoSurveyStateException();
    }

    public String showResults(Notifiable notifiable, Survey survey) {

        if (notifiable != null) {
            return notifiable.resultsFormat(survey);
        }

        Project project = survey.getProject();
        Discipline discipline = project.getDiscipline();

        return discipline.getName() + " - " + project.getName() + " - " +
                survey.getNumAnswers() + " respostas - " + survey.getAverageHours() + " horas\n";
    }
}
