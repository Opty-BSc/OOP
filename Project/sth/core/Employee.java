package sth.core;

class Employee extends Person implements java.io.Serializable {

    private static final long serialVersionUID = 123312893212L;

    Employee(Integer id, int phoneNumber, String name) {
        super(id, phoneNumber, name);
    }

    @Override
    String getType() {
        return "FUNCIONÁRIO";
    }

    @Override
    String getInfo() {
        return "";
    }
}
