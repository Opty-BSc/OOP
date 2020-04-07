package sth.core;

import sth.core.exception.*;

class SurveyCreated implements SurveyState {

    private static final long serialVersionUID = 324783247834L;

    public void cancel(Survey survey) {
        survey.getProject().removeSurvey();
    }

    public void open(Survey survey) throws SurveyStateException {

        if (survey.getProject().isOpen()) {
            throw new OpeningSurveyStateException();
        }
        survey.setState(new SurveyOpened());

        Project project = survey.getProject();
        Discipline discipline = project.getDiscipline();
        String message = survey.getOpenNotification(project.getName(), discipline.getName());

        discipline.sendNotifications(new Notification(message));
    }

    public void close(Survey survey) throws SurveyStateException {
        throw new ClosingSurveyStateException();
    }

    public void finish(Survey survey) throws SurveyStateException {
        throw new FinishingSurveyStateException();
    }

    public void registerAnswer(Survey survey, Student student, Survey.Answer answer) throws SurveyStateException {
        throw new NoSurveyStateException();
    }

    public String showResults(Notifiable notifiable, Survey survey) {
        Project project = survey.getProject();
        Discipline discipline = project.getDiscipline();

        return discipline.getName() + " - " + project.getName() + " (por abrir)\n";
    }
}
