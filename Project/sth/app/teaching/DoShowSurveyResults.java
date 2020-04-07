package sth.app.teaching;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.NoSuchDisciplineException;
import sth.app.exception.NoSurveyException;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSurveyStateException;
import sth.core.exception.SurveyStateException;

/**
 * 4.4.5. Show survey results.
 */
public class DoShowSurveyResults extends sth.app.common.ProjectCommand {


  /**
   * @param receiver
   */
  public DoShowSurveyResults(SchoolManager receiver) {
    super(Label.SHOW_SURVEY_RESULTS, receiver);
  }

  /** @see sth.app.common.ProjectCommand#myExecute() */
  @Override
  public final void myExecute() throws DialogException, NoSuchDisciplineIdException, NoSuchProjectIdException {

    String results = null;

    try {
      results = _receiver.showSurveyResults(_discipline.value(), _project.value());
    } catch (NoSurveyStateException e) {
      throw new NoSurveyException(_discipline.value(), _project.value());
    } catch (SurveyStateException e) {
      e.printStackTrace();
    }

    if (results != null) {
      _display.add(results);
      _display.display();
    }
  }

}
