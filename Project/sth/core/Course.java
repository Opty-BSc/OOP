package sth.core;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;

class Course implements java.io.Serializable {

    private static final long serialVersionUID = 359082348327L;

    private final int _MAX_REPRESENTATIVES = 7;

    private String _name;
    private Map<String, Discipline> _disciplines;
    private Map<Integer, Student> _students;
    private List<Integer> _representatives;

    Course(String name) {
        _name = name;
        _disciplines = new HashMap<String, Discipline>();
        _students = new HashMap<Integer, Student>();
        _representatives = new ArrayList<Integer>();
    }

    Discipline parseDiscipline(String disciplineName) {
        Discipline discipline = getDiscipline(disciplineName);

        if (discipline == null) {
            discipline = new Discipline(disciplineName,this);
            addDiscipline(discipline);

            for (Integer i : _representatives) {
                discipline.subscribe(_students.get(i));
            }
        }
        return discipline;
    }

    String getName() {
        return _name;
    }

    Discipline getDiscipline(String disciplineName) {
        for (Discipline d: _disciplines.values()) {
            if (d.getName().equals(disciplineName)) {
                return d;
            }
        }
        return null;
    }

    Collection<Discipline> getDisciplines() {
        return _disciplines.values();
    }

    boolean addDiscipline(Discipline discipline) {
        if (discipline != null && !_disciplines.containsValue(discipline)) {
            _disciplines.put(discipline.getName(), discipline);
            return true;
        }
        return false;
    }

    boolean addStudent(Student student) {
        if (student != null && !_students.containsValue(student)) {
            _students.put(student.getId(), student);
            return true;
        }
        return false;
    }

    boolean addRepresentative(Student student) {
        if (student != null && _representatives.size() < _MAX_REPRESENTATIVES) {
            for (Integer i: _representatives) {
                if (_students.get(i).equals(student)) {
                    return false;
                }
            }
            _representatives.add(student.getId());

            for (Discipline d : _disciplines.values()) {
                d.subscribe(student);
            }
            return true;
        }
        return false;
    }

    boolean removeRepresentative(Student student) {

        if (student != null) {
            for (Integer i : _representatives) {
                if (_students.get(i).equals(student)) {
                    for (Discipline d : _disciplines.values()) {
                        if (!student.getDisciplines().contains(d)) {
                            d.unsubscribe(student);
                        }
                    }
                    _representatives.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Course) {
            Course course = (Course)obj;
            return _name.equals(course.getName());
        }
        return false;
    }

    @Override
    public String toString() {
        return _name;
    }
}
