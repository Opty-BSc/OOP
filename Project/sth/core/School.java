package sth.core;

import sth.core.exception.BadEntryException;
import sth.core.exception.NoSuchPersonIdException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * School represents the University and therefore contains its Persons and Courses.
 * @author Sara Machado
 * @author Ricardo Grade
 */
class School implements java.io.Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201810051538L;

    /** Map which associates a Person Identifier to the respective Person */
    private Map<Integer, Person> _persons;
    /** Map which associates a Course Identifier to the respective Course */
    private Map<String, Course> _courses;

    /** Initializes School attributes */
    School() {
        _persons = new HashMap<Integer, Person>();
        _courses = new HashMap<String, Course>();
    }

    /**
     * From a given import filename try to import its content
     * @see sth.core.Parser#parseFile
     * @param filename Name of the import file
     * @throws BadEntryException When file content format is inappropriate
     * @throws IOException When the file does not exist or if there is an
     *                     error opening or closing the file
    */
    void importFile(String filename) throws IOException, BadEntryException {
        Parser parser = new Parser(this);

        parser.parseFile(filename);
    }

    /**
     * Prevents creating equal Courses by given the existing one
     * or creating a new one
     * @param courseName Name of the Course
     * @return The Course with the given name, if already exists,
     *         otherwise returns a new one
     */
    Course parseCourse(String courseName) {
        Course course = getCourse(courseName);

        if (course == null) {
            course = new Course(courseName);
            addCourse(course);
        }
        return course;
    }

    /**
     * From a given course name provides the associated Course
     * @param courseName Name of the Course
     * @return The course with the given name, if already exists,
     *         otherwise returns null
     */
    Course getCourse(String courseName) {
        return _courses.get(courseName);
    }

    /**
     * Adds the course to the School if it does not yet exist
     * @param course Object of type Course
     * @return True, if the course was added to the School,
     *         otherwise returns False
     */
    boolean addCourse(Course course) {
        if (course != null && !_courses.containsValue(course)) {
            _courses.put(course.getName(), course);
            return true;
        }
        return false;
    }

    /**
     * Adds the person to the School if it does not yet exist
     * @param person Object of type Person
     * @return True, if the person was added to the School,
     *         otherwise returns False
     */
    boolean addPerson(Person person) {
        if (person != null && !_persons.containsValue(person)) {
            _persons.put(person.getId(), person);
            return true;
        }
        return false;
    }

    /**
     * Provides all the Persons in the School
     * @return A Collection of all Persons in the School
     */
    Collection<Person> getAllUsers() {
        return new ArrayList<Person>(_persons.values());
    }

    /**
     * Provides the Person with the given identifier if it already exists
     * @param id Person identifier
     * @return The Person with the given identifier
     * @throws NoSuchPersonIdException When there is no Person
     *                                 with the given identifier
     */
    Person getPersonById(Integer id) throws NoSuchPersonIdException {
        if (_persons.containsKey(id)) {
            return _persons.get(id);
        } else {
            throw new NoSuchPersonIdException(id);
        }
    }
}
