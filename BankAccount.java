public class BankAccount {

    private int accountNumber;
    private String name;
    private double balance;
    private int pin;
    private int wrongAttempts = 0;
    private boolean isBlocked = false;

    // ✅ Constructor with PIN
    public BankAccount(int accountNumber, String name, double balance, int pin) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
        this.pin = pin;
    }
    public boolean isBlocked() {
    return isBlocked;
}

    // ✅ Deposit money
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    // ✅ Withdraw money (balance check)
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount!");
        } else if (amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Insufficient Balance!");
        }
    }

    // ✅ Display account details (PIN NOT shown)
    public void display() {
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Name           : " + name);
        System.out.println("Balance        : " + balance);
    }

    // ✅ Getters
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public int getPin() {
        return pin;
    }

    // ✅ PIN validation method (VERY IMPORTANT)
    public boolean validatePin(int enteredPin) {

    if (isBlocked) {
        System.out.println("Account is Blocked due to multiple wrong PIN attempts!");
        return false;
    }

    if (this.pin == enteredPin) {
        wrongAttempts = 0;   // reset attempts
        return true;
    } else {
        wrongAttempts++;
        System.out.println("Incorrect PIN!");

        if (wrongAttempts >= 3) {
            isBlocked = true;
            System.out.println("Account Blocked due to 3 wrong attempts!");
        }
        return false;
    }
}
}
