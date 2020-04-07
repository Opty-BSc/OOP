package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Dialog;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.NoSuchDisciplineException;
import sth.core.SchoolManager;
import sth.core.exception.NoSuchDisciplineIdException;

/**
 * 4.4.4. Show course students.
 */
public class DoShowDisciplineStudents extends Command<SchoolManager> {

    private Input<String> _disciplineName;

    /**
    * @param receiver
    */
    public DoShowDisciplineStudents(SchoolManager receiver) {
        super(Label.SHOW_COURSE_STUDENTS, receiver);

        _disciplineName = _form.addStringInput(Message.requestDisciplineName());
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException {
        _form.parse();

        String students = null;

        try {
            students = _receiver.showDisciplineStudents(_disciplineName.value());

        } catch (NoSuchDisciplineIdException ex) {
            throw new NoSuchDisciplineException(_disciplineName.value());
        }

        if (students != null) {
            _display.add(students);
            _display.display();

        }
    }
}
