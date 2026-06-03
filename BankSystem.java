import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class BankSystem {

    static ArrayList<BankAccount> accounts = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadFromFile();

        while (true) {
            System.out.println("\n1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Display Account");
            System.out.println("5. Transfer Money");
            System.out.println("6. Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1: createAccount(); break;
                case 2: deposit(); break;
                case 3: withdraw(); break;
                case 4: display(); break;
                case 5: transfer(); break;
                case 6: 
                    saveToFile();
                    System.exit(0);
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    // ✅ CREATE ACCOUNT WITH PIN
    static void createAccount() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Initial Balance: ");
        double balance = sc.nextDouble();

        System.out.print("Set 4-digit PIN: ");
        int pin = sc.nextInt();

        accounts.add(new BankAccount(accNo, name, balance, pin));
        System.out.println("Account Created Successfully!");
        saveToFile();
    }

    static BankAccount findAccount(int accNo) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber() == accNo) {
                return acc;
            }
        }
        return null;
    }

    // ✅ DEPOSIT WITH PIN
    static void deposit() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        BankAccount acc = findAccount(accNo);

        if (acc != null) {
            System.out.print("Enter PIN: ");
            int pin = sc.nextInt();

            if (acc.validatePin(pin)) {
                System.out.print("Enter Amount: ");
                double amount = sc.nextDouble();
                acc.deposit(amount);
                saveToFile();
                System.out.println("Amount Deposited!");
            } else {
                System.out.println("Incorrect PIN!");
            }
        } else {
            System.out.println("Account Not Found!");
        }
    }

    // ✅ WITHDRAW WITH PIN
    static void withdraw() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        BankAccount acc = findAccount(accNo);

        if (acc != null) {
            System.out.print("Enter PIN: ");
            int pin = sc.nextInt();

            if (acc.validatePin(pin)) {
                System.out.print("Enter Amount: ");
                double amount = sc.nextDouble();
                acc.withdraw(amount);
                saveToFile();
            } else {
                System.out.println("Incorrect PIN!");
            }
        } else {
            System.out.println("Account Not Found!");
        }
    }

    static void display() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        BankAccount acc = findAccount(accNo);

        if (acc != null) {
            acc.display();
        } else {
            System.out.println("Account Not Found!");
        }
    }

    // ✅ TRANSFER WITH PIN
    static void transfer() {
        System.out.print("Enter Sender Account Number: ");
        int senderAccNo = sc.nextInt();

        System.out.print("Enter Receiver Account Number: ");
        int receiverAccNo = sc.nextInt();

        BankAccount sender = findAccount(senderAccNo);
        BankAccount receiver = findAccount(receiverAccNo);

        if (sender != null && receiver != null) {

            System.out.print("Enter Sender PIN: ");
            int pin = sc.nextInt();

            if (!sender.validatePin(pin)) {
                System.out.println("Incorrect PIN!");
                return;
            }

            System.out.print("Enter Amount to Transfer: ");
            double amount = sc.nextDouble();

            if (sender.getBalance() >= amount) {
                sender.withdraw(amount);
                receiver.deposit(amount);
                saveToFile();
                System.out.println("Transfer Successful!");
            } else {
                System.out.println("Insufficient Balance!");
            }

        } else {
            System.out.println("One or Both Accounts Not Found!");
        }
    }

    // ✅ SAVE FILE (WITH PIN)
    static void saveToFile() {
        try {
            PrintWriter writer = new PrintWriter("accounts.txt");

            for (BankAccount acc : accounts) {
                writer.println(
                    acc.getAccountNumber() + "," +
                    acc.getName() + "," +
                    acc.getBalance() + "," +
                    acc.getPin()
                );
            }

            writer.close();
        } catch (Exception e) {
            System.out.println("Error Saving File");
        }
    }

    // ✅ LOAD FILE (WITH PIN)
    static void loadFromFile() {
        try {
            File file = new File("accounts.txt");
            if (!file.exists()) return;

            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");

                int accNo = Integer.parseInt(parts[0]);
                String name = parts[1];
                double balance = Double.parseDouble(parts[2]);
                int pin = Integer.parseInt(parts[3]);

                accounts.add(new BankAccount(accNo, name, balance, pin));
            }

            fileScanner.close();
        } catch (Exception e) {
            System.out.println("Error Loading File");
        }
    }
}
