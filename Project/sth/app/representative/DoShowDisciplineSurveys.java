package sth.app.representative;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.NoSuchDisciplineException;
import sth.core.SchoolManager;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSurveyStateException;

//FIXME import other classes if needed

/**
 * 4.6.6. Show discipline surveys.
 */
public class DoShowDisciplineSurveys extends Command<SchoolManager> {

    Input<String> _discipline;

  /**
   * @param receiver
   */
  public DoShowDisciplineSurveys(SchoolManager receiver) {
    super(Label.SHOW_DISCIPLINE_SURVEYS, receiver);
    _discipline = _form.addStringInput(Message.requestDisciplineName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
      _form.parse();

      String results = null;
      try {
          results = _receiver.showDisciplineSurveys(_discipline.value());
      } catch (NoSuchDisciplineIdException e) {
          throw new NoSuchDisciplineException(_discipline.value());
      } catch (NoSurveyStateException e) {
          e.printStackTrace();
      }

      if (results != null) {
          _display.add(results);
          _display.display();
      }
  }

}
