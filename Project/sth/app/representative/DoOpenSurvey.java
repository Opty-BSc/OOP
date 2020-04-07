package sth.app.representative;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.NoSurveyException;
import sth.app.exception.OpeningSurveyException;
import sth.core.SchoolManager;

import sth.core.exception.*;

/**
 * 4.6.3. Open survey.
 */
public class DoOpenSurvey extends sth.app.common.ProjectCommand {
  /**
   * @param receiver
   */
  public DoOpenSurvey(SchoolManager receiver) {
    super(Label.OPEN_SURVEY, receiver);
    //FIXME initialize input fields if needed
  }

  /** @see sth.app.common.ProjectCommand#myExecute() */ 
  @Override
  public final void myExecute() throws DialogException, NoSuchDisciplineIdException, NoSuchProjectIdException {

      try {
          _receiver.openSurvey(_discipline.value(), _project.value());

      } catch (OpeningSurveyStateException e) {
          throw new OpeningSurveyException(_discipline.value(), _project.value());
      } catch (NoSurveyStateException e) {
          throw new NoSurveyException(_discipline.value(), _project.value());
      } catch (SurveyStateException e) {
          e.printStackTrace();
      }
  }
}
