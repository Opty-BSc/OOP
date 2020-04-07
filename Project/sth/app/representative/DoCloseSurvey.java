package sth.app.representative;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.ClosingSurveyException;
import sth.app.exception.NoSurveyException;
import sth.core.SchoolManager;

import sth.core.exception.*;

/**
 * 4.5.4. Close survey.
 */
public class DoCloseSurvey extends sth.app.common.ProjectCommand {
  /**
   * @param receiver
   */
  public DoCloseSurvey(SchoolManager receiver) {
    super(Label.CLOSE_SURVEY, receiver);
    //FIXME initialize input fields if needed
  }

  /** @see sth.app.common.ProjectCommand#myExecute() */
  @Override
  public final void myExecute() throws NoSuchProjectIdException, NoSuchDisciplineIdException, DialogException {

      try {
          _receiver.closeSurvey(_discipline.value(), _project.value());

      } catch (ClosingSurveyStateException e) {
          throw new ClosingSurveyException(_discipline.value(), _project.value());
      } catch (NoSurveyStateException e) {
          throw new NoSurveyException(_discipline.value(), _project.value());
      } catch (SurveyStateException e) {
          e.printStackTrace();
      }
  }
}
