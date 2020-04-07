package sth.core.exception;

public class DuplicateProjectIdException extends Exception {

    private static final long serialVersionUID = 454627373822L;

    private String _projectName;

    public DuplicateProjectIdException(String id) { _projectName = id; }

    public String getId() { return _projectName; }
}
