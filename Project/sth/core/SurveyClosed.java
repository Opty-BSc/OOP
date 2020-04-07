package sth.core;

import sth.core.exception.NoSurveyStateException;
import sth.core.exception.SurveyStateException;

class SurveyClosed implements SurveyState {

    private static final long serialVersionUID = 546731265473L;

    public void cancel(Survey survey) {
        survey.setState(new SurveyOpened());
    }

    public void open(Survey survey) {
        survey.setState(new SurveyOpened());

        Project project = survey.getProject();
        Discipline discipline = project.getDiscipline();
        String message = survey.getOpenNotification(project.getName(), discipline.getName());

        discipline.sendNotifications(new Notification(message));
    }

    public void close(Survey survey) {}

    public void finish(Survey survey) {
        survey.calcHours();
        survey.setState(new SurveyFinalized());

        Project project = survey.getProject();
        Discipline discipline = project.getDiscipline();
        String message = survey.getFinishNotification(project.getName(), discipline.getName());

        discipline.sendNotifications(new Notification(message));
    }

    public void registerAnswer(Survey survey, Student student, Survey.Answer answer) throws SurveyStateException {
        throw new NoSurveyStateException();
    }

    public String showResults(Notifiable notifiable, Survey survey) {
        Project project = survey.getProject();
        Discipline discipline = project.getDiscipline();

        return discipline.getName() + " - " + project.getName() + " (fechado)\n";
    }
}
