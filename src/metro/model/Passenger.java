package metro.model;

// CONCEPT: Single Inheritance (Passenger extends Person)
// CONCEPT: Abstract Class with Abstract Method
public abstract class Passenger extends Person {
    protected String passengerType;

    public Passenger(String name, String phone, String passengerType) {
        super(name, phone); // Call parent Person constructor
        this.passengerType = passengerType;
    }

    public String getPassengerType() {
        return passengerType;
    }

    // CONCEPT: Abstract Method (subclasses define their specific discount percentage)
    public abstract double getDiscountRate();

    @Override
    public void displayInfo() {
        System.out.println("Passenger: " + name + " (" + passengerType + ") | Phone: " + phone);
    }
}
