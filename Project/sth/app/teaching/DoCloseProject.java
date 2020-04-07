package sth.app.teaching;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.SurveyStateException;

/**
 * 4.4.2. Close project.
 */
public class DoCloseProject extends sth.app.common.ProjectCommand {

    /**
    * @param receiver
    */
    public DoCloseProject(SchoolManager receiver) {
      super(Label.CLOSE_PROJECT, receiver);
    }

    /** @see sth.app.common.ProjectCommand#myExecute() */
    @Override
    public final void myExecute() throws NoSuchDisciplineIdException, NoSuchProjectIdException {

        try {

            _receiver.closeProject(_discipline.value(), _project.value());
        } catch (SurveyStateException e){
            e.printStackTrace();
        }
    }

}
