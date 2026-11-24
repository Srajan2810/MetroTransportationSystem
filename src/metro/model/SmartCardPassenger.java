package metro.model;

import metro.exception.InsufficientBalanceException;

// CONCEPT: Multilevel Inheritance (Person -> Passenger -> SmartCardPassenger)
public class SmartCardPassenger extends Passenger {
    private String cardNumber;
    private double balance;

    public SmartCardPassenger(String name, String phone, String cardNumber, double initialBalance) {
        super(name, phone, "Smart Card Commuter"); // Call parent constructor
        this.cardNumber = cardNumber;
        this.balance = initialBalance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void recharge(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.printf("Recharged ₹%.2f. Current Balance: ₹%.2f\n", amount, balance);
        }
    }

    // Deducts fare and throws custom exception if balance is low
    public void deductFare(double amount) throws InsufficientBalanceException {
        if (amount > balance) {
            throw new InsufficientBalanceException(balance, amount);
        }
        balance -= amount;
    }

    // CONCEPT: Method Overriding (Smart Card users get a 10% discount)
    @Override
    public double getDiscountRate() {
        return 0.10; // 10% discount
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.printf("  Card No: %s | Active Balance: ₹%.2f (10%% Discount)\n", cardNumber, balance);
    }
}
