import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {
    private String userId;
    private String pin;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
    }
    public String getUserId() {
        return userId;
    }
    public String getPin() {
        return pin;
    }
}
class Account {
    private String accountNumber;
    private double balance;
    private Map<String, Double> transactionHistory;
    public Account(String accountNumber, double initialBal) {
        this.accountNumber = accountNumber;
        this.balance = initialBal;
        this.transactionHistory = new HashMap<>();
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public double getBalance() {
        return balance;
    }
    public Map<String, Double> getTransactionHistory() {
        return transactionHistory;
    }
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.put("Deposit", amount);
        }
    }
    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            transactionHistory.put("Withdrawal", amount);
            return true;
        } else {
            return false;
        }
    }
    public void transfer(Account targetAccount, double amount) {
        if (withdraw(amount)) {
            targetAccount.deposit(amount);
            transactionHistory.put("Transfer to " + targetAccount.getAccountNumber(), amount);
        }
    }
}
public class Atm {
    private static Map<String, User> users = new HashMap<>();
    private static Map<String, Account> accounts = new HashMap<>();
    private static User currentUser;
    private static Account currentAccount;
    public static void main(String[] args) {
        // Initialize some sample users and accounts
        users.put("chandini", new User("chandini", "098321"));
        accounts.put("venky123", new Account("venky123", 12000.0));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the ATM.");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
        if (authenticateUser(userId, pin)) {
            System.out.println("Authentication successful.");
            showMenu();
        } else {
            System.out.println("Authentication failed. Exiting...");
        }}
    private static boolean authenticateUser(String userId, String pin) {
        User user = users.get(userId);
        if (user != null && user.getPin().equals(pin)) {
            currentUser = user;
            currentAccount = accounts.get("venky123"); // Assuming a single account per user for simplicity
            return true;
        }
        return false;
    }
    private static void showMenu() {
        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. View Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Check Balance");
            System.out.println("6. Quit");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:viewTransactionHistory();
                    break;
                case 2:withdraw();
                    break;
                case 3:deposit();
                    break;
                case 4:transfer();
                    break;
                case 5:checkBalance();
                    break;
                case 6:System.out.println("Thank you for using the ATM. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
    private static void viewTransactionHistory() {
        Map<String, Double> transactions = currentAccount.getTransactionHistory();
        System.out.println("Transaction History:");
        for (Map.Entry<String, Double> entry : transactions.entrySet()) {
            System.out.println(entry.getKey() + ": RS" + entry.getValue());
        }
    }
    private static void withdraw() {
        System.out.print("Enter the withdrawal amount: RS");
        Scanner scanner = new Scanner(System.in);
        double amount = scanner.nextDouble();
        if (currentAccount.withdraw(amount)) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Withdrawal failed. Insufficient balance or invalid amount.");
        }
    }
    private static void deposit() {
        System.out.print("Enter the deposit amount: RS");
        Scanner scanner = new Scanner(System.in);
        double amount = scanner.nextDouble();

        currentAccount.deposit(amount);
        System.out.println("Deposit successful.");
    }
    private static void transfer() {
        System.out.print("Enter the recipient's account number: ");
        Scanner scanner = new Scanner(System.in);
        String targetAccountNumber = scanner.nextLine();
        System.out.print("Enter the transfer amount: RS");
        double amount = scanner.nextDouble();
        Account targetAccount = accounts.get(targetAccountNumber);
        if (targetAccount != null) {
            currentAccount.transfer(targetAccount, amount);
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Recipient account not found.");
        }
    }
    private static void checkBalance() {
        double balance = currentAccount.getBalance();
        System.out.println("Current Balance: RS" + balance);
    }
}
