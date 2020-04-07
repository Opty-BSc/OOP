package sth.app.representative;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.NoSurveyException;
import sth.app.exception.NonEmptySurveyException;
import sth.app.exception.SurveyFinishedException;
import sth.core.SchoolManager;

import sth.core.exception.*;

/**
 * 4.5.2. Cancel survey.
 */
public class DoCancelSurvey extends sth.app.common.ProjectCommand {

  /**
   * @param receiver
   */
  public DoCancelSurvey(SchoolManager receiver) {
      super(Label.CANCEL_SURVEY, receiver);
  }

  /** @see sth.app.common.ProjectCommand#myExecute() */
  @Override
  public final void myExecute() throws NoSuchProjectIdException, NoSuchDisciplineIdException, DialogException {

    try {
      _receiver.cancelSurvey(_discipline.value(), _project.value());

    } catch (FinishedSurveyStateException e) {
      throw new SurveyFinishedException(_discipline.value(), _project.value());
    } catch (NonEmptySurveyStateException e) {
      throw new NonEmptySurveyException(_discipline.value(), _project.value());
    } catch (NoSurveyStateException e) {
      throw new NoSurveyException(_discipline.value(), _project.value());
    } catch (SurveyStateException e) {
      e.printStackTrace();
    }
  }
}
