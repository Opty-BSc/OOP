package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.app.exception.NoSuchDisciplineException;
import sth.app.exception.NoSuchPersonException;
import sth.core.SchoolManager;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchPersonIdException;

public class DoRemoveStudent extends Command<SchoolManager> {

    Input<String> _disciplineName;
    Input<Integer> _studentId;

    public DoRemoveStudent(SchoolManager receiver) {
        super("Remove Student", receiver);

        _disciplineName = _form.addStringInput(Message.requestDisciplineName());
        _studentId = _form.addIntegerInput("Student id: ");
    }

    @Override
    public final void execute() throws NoSuchDisciplineException, NoSuchPersonException {
        _form.parse();

        try {
            _receiver.removeStudent(_disciplineName.value(), _studentId.value());

        } catch (NoSuchDisciplineIdException ex) {
            throw new NoSuchDisciplineException(ex.getId());
        } catch (NoSuchPersonIdException ex) {
            throw new NoSuchPersonException(ex.getId());
        }
    }
}
