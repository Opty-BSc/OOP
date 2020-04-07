package sth.core;

class Notification implements java.io.Serializable {

    private static final long serialVersionUID = 723473274727L;

    private String _message;

    Notification(String message) {
        _message = message;
    }

    @Override
    public String toString() {
        return _message;
    }
}
