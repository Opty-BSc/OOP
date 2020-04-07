package sth.core;

import sth.core.exception.FinishingSurveyStateException;
import sth.core.exception.NonEmptySurveyStateException;
import sth.core.exception.SurveyStateException;

class SurveyOpened implements SurveyState {

    private static final long serialVersionUID = 574543678567L;

    public void cancel(Survey survey) throws SurveyStateException {
        if (survey.hasAnswers()) {
            throw new NonEmptySurveyStateException();
        }
        survey.getProject().removeSurvey();
    }

    public void open(Survey survey) {}

    public void close(Survey survey) {
        survey.setState(new SurveyClosed());
    }

    public void finish(Survey survey) throws SurveyStateException {
        throw new FinishingSurveyStateException();
    }

    public void registerAnswer(Survey survey, Student student, Survey.Answer answer) {
        survey.addAnswer(student, answer);
    }

    public String showResults(Notifiable notifiable, Survey survey) {
        Project project = survey.getProject();
        Discipline discipline = project.getDiscipline();

        return discipline.getName() + " - " + project.getName() + " (aberto)\n";
    }
}
