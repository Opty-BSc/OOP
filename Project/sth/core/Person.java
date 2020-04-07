package sth.core;

import sth.core.exception.BadEntryException;

import java.util.Comparator;

abstract class Person implements Comparable<Person>, Comparator<Person>, java.io.Serializable {

    private static final long serialVersionUID = 234924832428L;

    private Integer _id;
    private int _phoneNumber;
    private String _name;

    Person(Integer id, int phoneNumber, String name) {
        _id = id;
        _phoneNumber = phoneNumber;
        _name = name;
    }

    /**
     * Parses the context information for a person from the import file.
     * This method defines the default behavior: no extra information is needed
     * thus it throws the exception.
     **/
    void parseContext(String context, School school) throws BadEntryException {
        throw new BadEntryException("Should not have extra context: " + context);
    }

    Integer getId() {
        return _id;
    }

    int getPhoneNumber() {
        return _phoneNumber;
    }

    String getName() {
        return _name;
    }

    void setPhoneNumber(int phoneNumber) {
        _phoneNumber = phoneNumber;
    }

    abstract String getType();

    abstract String getInfo();

    @Override
    public int compareTo(Person p) {
        return _id - p.getId();
    }

    @Override
    public int compare(Person p1, Person p2) {
        return p1.getName().compareTo(p2.getName());
    }

    @Override
    public String toString(){
        return getType() + "|" + _id + "|" + _phoneNumber + "|" + _name + "\n" + getInfo();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person) {
            Person person = (Person)obj;
            return _id.equals(person.getId());
        }
        return false;
    }
}
