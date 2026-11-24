package metro.model;

// CONCEPT: Abstract Class (Root of Inheritance Hierarchy)
public abstract class Person {
    protected String name;
    protected String phone;

    public Person(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    // CONCEPT: Abstract Method (must be implemented by child classes)
    public abstract void displayInfo();
}
