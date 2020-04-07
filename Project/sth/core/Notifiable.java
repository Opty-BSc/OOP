package sth.core;

import sth.core.exception.NoSuchDisciplineIdException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

abstract class Notifiable extends Person implements java.io.Serializable, Observer {

    private static final long serialVersionUID = 347837424242L;

    private List<Notification> _notifications;
    private List<Discipline> _disciplines;
    private int _MAX_DISCIPLINES;

    Notifiable(Integer id, int phoneNumber, String name) {
        super(id, phoneNumber, name);
        _notifications = new ArrayList<Notification>();
        _disciplines = new ArrayList<Discipline>();
        _MAX_DISCIPLINES = 6;
    }

    boolean addDiscipline(Discipline discipline) {
        if (discipline != null && !_disciplines.contains(discipline) && _disciplines.size() < _MAX_DISCIPLINES){
            _disciplines.add(discipline);
            return true;
        }
        return false;
    }

    Discipline getDiscipline(String disciplineName) throws NoSuchDisciplineIdException {

        for (Discipline d : _disciplines) {
            if (disciplineName.equals(d.getName())) {
                return d;
            }
        }
        throw new NoSuchDisciplineIdException(disciplineName);
    }

    Collection<Discipline> getDisciplines() { return _disciplines; }

    Collection<Notification> getNotifications() {

        List<Notification> notifications = new ArrayList<Notification>(_notifications);
        _notifications = new ArrayList<Notification>();

        return notifications;
    }

    abstract String resultsFormat(Survey survey);

    @Override
    public void update(Notification notification) {
        _notifications.add(notification);
    }

    @Override
    abstract String getType();

    @Override
    String getInfo() {

        List<String> disciplines = new ArrayList<String>();
        for (Discipline d: getDisciplines()) {
            disciplines.add(d.toString());
        }
        Collections.sort(disciplines);

        String result = "";
        for (String s: disciplines) {
            result = result.concat(s);
        }
        return result;
    }

    void removeDiscpline(Discipline dis) throws NoSuchDisciplineIdException {

        if (!_disciplines.contains(dis)) {
            throw new NoSuchDisciplineIdException(dis.getName());
        }

        dis.unsubscribe(this);
        _disciplines.remove(dis);
    }
}
