package sth.app.person;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Display;
import pt.tecnico.po.ui.Form;
import sth.core.SchoolManager;

/**
 * 4.2.3. Show all persons.
 */
public class DoShowAllPersons extends Command<SchoolManager> {

    /**
    * @param receiver
    */
    public DoShowAllPersons(SchoolManager receiver) {
        super(Label.SHOW_ALL_PERSONS, receiver);
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() {

        String persons = _receiver.showAllPersons();

        if (persons != null) {
            _display.add(persons);
            _display.display();
        }
    }
}
