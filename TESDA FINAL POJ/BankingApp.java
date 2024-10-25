import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {
    private int id;
    private int pin;
    private String name;
    private double balance;

    public User(int id, int pin, String name, double balance) {
        this.id = id;
        this.pin = pin;
        this.name = name;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public int getPin() {
        return pin;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance(double amount) {
        balance += amount;
    }

    public boolean transferMoney(User recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recipient.addBalance(amount);
            return true;
        }
        return false;
    }
}

public class BankingApp {
    private static Map<Integer, User> users = new HashMap<>();
    private static User loggedInUser  = null;

    public static void main(String[] args) {
        initializeUsers();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the Banking App");
            System.out.print("Enter User ID: ");
            int userId = scanner.nextInt();
            System.out.print("Enter PIN: ");
            int pin = scanner.nextInt();

            if (login(userId, pin)) {
                int choice;
                do {
                    System.out.println("\n1. Check Balance");
                    System.out.println("2. Cash-In");
                    System.out.println("3. Money Transfer");
                    System.out.println("4. Logout");
                    System.out.print("Choose an option: ");
                    choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            System.out.println("Current Balance: " + loggedInUser .getBalance());
                            break;
                        case 2:
                            System.out.print("Enter amount to add: ");
                            double amountToAdd = scanner.nextDouble();
                            loggedInUser .addBalance(amountToAdd);
                            System.out.println("New Balance: " + loggedInUser .getBalance());
                            break;
                        case 3:
                            System.out.print("Enter recipient User ID: ");
                            int recipientId = scanner.nextInt();
                            System.out.print("Enter amount to transfer: ");
                            double amountToTransfer = scanner.nextDouble();
                            User recipient = users.get(recipientId);
                            if (recipient != null && loggedInUser .transferMoney(recipient, amountToTransfer)) {
                                System.out.println("Transfer successful! New Balance: " + loggedInUser .getBalance());
                            } else {
                                System.out.println("Transfer failed! Check recipient ID or amount.");
                            }
                            break;
                        case 4:
                            loggedInUser  = null;
                            System.out.println("Logged out successfully.");
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                } while (choice != 4);
            } else {
                System.out.println("Invalid login credentials. Please try again.");
            }
        }
    }

    private static void initializeUsers() {
        users.put(412435, new User(412435, 7452, "Chris Sandoval", 32000));
        users.put(264863, new User(264863, 1349, "Marc Yim", 1000));
    }

    private static boolean login(int userId, int pin) {
        User user = users.get(userId);
        if (user != null && user.getPin() == pin) {
            loggedInUser  = user;
            System.out.println("Login successful! Welcome, " + user.getName());
            return true;
        }
        return false;
    }
}
