package sth.app.student;

import pt.tecnico.po.ui.Command;
import sth.core.SchoolManager;

public class DoShowSubmissions extends Command<SchoolManager> {

    public DoShowSubmissions(SchoolManager receiver) {
        super("Show all Submissions", receiver);
    }

    @Override
    public final void execute() {
        _form.parse();

        String result = _receiver.showSubmissions();

        if (result != null) {
            _display.add(result);
            _display.display();
        }
    }
}
