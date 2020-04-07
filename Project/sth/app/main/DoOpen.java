package sth.app.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.NoSuchPersonException;
import sth.core.exception.NoSuchPersonIdException;
import sth.core.SchoolManager;

/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<SchoolManager> {

    private Input<String> _filename;
  
    /**
    * @param receiver
    */
    public DoOpen(SchoolManager receiver) {
        super(Label.OPEN, receiver);
        _filename = _form.addStringInput(Message.openFile());
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException {
        _form.parse();

        String notifications = null;
        try {
            notifications = _receiver.load(_filename.value());

        } catch (NoSuchPersonIdException e){
            throw new NoSuchPersonException(e.getId());
        } catch (FileNotFoundException fnfe) {
            _display.popup(Message.fileNotFound());
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        if (notifications != null){
            _display.add(notifications);
            _display.display();
        }
    }
}
