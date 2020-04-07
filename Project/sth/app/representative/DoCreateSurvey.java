package sth.app.representative;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.*;
import sth.core.SchoolManager;

import sth.core.exception.*;

/**
 * 4.5.1. Create survey.
 */
public class DoCreateSurvey extends sth.app.common.ProjectCommand {

  /**
   * @param receiver
   */
  public DoCreateSurvey(SchoolManager receiver) {
    super(Label.CREATE_SURVEY, receiver);
    //FIXME initialize input fields if needed
  }

  /** @see sth.app.common.ProjectCommand#myExecute() */ 
  @Override
  public final void myExecute() throws DialogException, NoSuchDisciplineIdException, NoSuchProjectIdException {

      try {
          _receiver.createSurvey(_discipline.value(), _project.value());

      } catch (DuplicateSurveyStateException e) {
          throw new DuplicateSurveyException(_discipline.value(), _project.value());
      } catch (NoSurveyStateException e) {
          throw new NoSurveyException(_discipline.value(), _project.value());
      } catch (SurveyStateException e) {
          e.printStackTrace();
      }
  }
}
