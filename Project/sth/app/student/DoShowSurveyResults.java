package sth.app.student;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchProjectIdException;
import sth.app.exception.NoSurveyException;
import sth.core.exception.NoSurveyStateException;
import sth.core.exception.SurveyStateException;

/**
 * 4.5.3. Show survey results.
 */
public class DoShowSurveyResults extends sth.app.common.ProjectCommand {

  /**
   * @param receiver
   */
  public DoShowSurveyResults(SchoolManager receiver) {
    super(Label.SHOW_SURVEY_RESULTS, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void myExecute() throws NoSuchProjectIdException, NoSuchDisciplineIdException, NoSurveyException {

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
