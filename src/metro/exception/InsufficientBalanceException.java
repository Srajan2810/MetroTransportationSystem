package metro.exception;

// CONCEPT: Custom Exception Handling
public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(double current, double required) {
        super(String.format("Insufficient Balance! Available: ₹%.2f, Required: ₹%.2f.", current, required));
    }
}
