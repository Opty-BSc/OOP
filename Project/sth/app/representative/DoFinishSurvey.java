package sth.app.representative;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.FinishingSurveyException;
import sth.app.exception.NoSurveyException;
import sth.core.SchoolManager;

//FIXME import other classes if needed

import sth.core.exception.*;

/**
 * 4.6.5. Finish survey.
 */
public class DoFinishSurvey extends sth.app.common.ProjectCommand {

  /**
   * @param receiver
   */
  public DoFinishSurvey(SchoolManager receiver) {
    super(Label.FINISH_SURVEY, receiver);
    //FIXME initialize input fields if needed
  }

  /** @see sth.app.common.ProjectCommand#myExecute() */ 
  @Override
  public final void myExecute() throws DialogException, NoSuchDisciplineIdException, NoSuchProjectIdException {

    try {
      _receiver.finishSurvey(_discipline.value(), _project.value());

    } catch (FinishingSurveyStateException e) {
      throw new FinishingSurveyException(_discipline.value(), _project.value());
    } catch (NoSurveyStateException e) {
      throw new NoSurveyException(_discipline.value(), _project.value());
    } catch (SurveyStateException e) {
      e.printStackTrace();
    }
  }

}
