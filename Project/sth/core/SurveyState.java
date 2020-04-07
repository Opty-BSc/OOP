package sth.core;

import sth.core.exception.SurveyStateException;

interface SurveyState extends java.io.Serializable {

    void cancel(Survey survey) throws SurveyStateException;
    void open(Survey survey) throws SurveyStateException;
    void close(Survey survey) throws SurveyStateException;
    void finish(Survey survey) throws SurveyStateException;
    void registerAnswer(Survey survey, Student student, Survey.Answer answer) throws SurveyStateException;
    String showResults(Notifiable notifiable, Survey survey);
}
