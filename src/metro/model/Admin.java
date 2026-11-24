package metro.model;

// CONCEPT: Hierarchical Inheritance (Admin extends Person, parallel to Passenger)
public class Admin extends Person {
    private String adminId;

    public Admin(String name, String phone, String adminId) {
        super(name, phone); // CONCEPT: super(...) constructor call
        this.adminId = adminId;
    }

    public String getAdminId() {
        return adminId;
    }

    @Override
    public void displayInfo() {
        System.out.println("Admin Details: " + name + " | ID: " + adminId + " | Phone: " + phone);
    }
}
