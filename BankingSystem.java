import java.io.*;
import java.util.ArrayList;

public class BankingSystem {
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "accounts.dat";

    public static void main(String[] args) {
        loadAccounts();
        int choice;
        do {
            showMenu();
            choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1 -> createAccount();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> transfer();
                case 5 -> viewAccounts();
                case 6 -> sortAccounts();
                case 0 -> saveAccounts();
                default -> System.out.println("Invalid choice!");
            }
        } while(choice != 0);
    }

    private static void showMenu() {
        System.out.println("\n--- Simple Banking System ---");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. View Accounts");
        System.out.println("6. Sort Accounts");
        System.out.println("0. Exit");
    }

    private static void createAccount() {
        int accNum = getIntInput("Enter account number: ");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        double balance = getDoubleInput("Enter initial balance: ");
        Account account = new Account(accNum, name, balance);
        accounts.add(account);
        System.out.println("Account created successfully!");
    }

    private static void deposit() {
        Account acc = findAccount();
        if(acc != null) {
            double amount = getDoubleInput("Enter amount to deposit: ");
            acc.deposit(amount);
        }
    }

    private static void withdraw() {
        Account acc = findAccount();
        if(acc != null) {
            double amount = getDoubleInput("Enter amount to withdraw: ");
            acc.withdraw(amount);
        }
    }

    private static void transfer() {
        System.out.println("Source account:");
        Account source = findAccount();
        if(source != null) {
            System.out.println("Target account:");
            Account target = findAccount();
            if(target != null) {
                double amount = getDoubleInput("Enter amount to transfer: ");
                source.transfer(target, amount);
            }
        }
    }

    private static void viewAccounts() {
        if(accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            for(Account acc : accounts) {
                System.out.println(acc);
            }
        }
    }

    private static void sortAccounts() {
        System.out.println("Sort by: 1. Name  2. Balance");
        int choice = getIntInput("Enter choice: ");
        switch (choice) {
            case 1 -> accounts.sort(Comparator.comparing(Account::getName));
            case 2 -> accounts.sort(Comparator.comparingDouble(Account::getBalance));
            default -> System.out.println("Invalid choice!");
        }
        System.out.println("Accounts sorted!");
    }

    private static Account findAccount() {
        int accNum = getIntInput("Enter account number: ");
        for(Account acc : accounts) {
            if(acc.getAccountNumber() == accNum) return acc;
        }
        System.out.println("Account not found!");
        return null;
    }

    private static int getIntInput(String message) {
        while(true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Invalid input! Enter an integer.");
            }
        }
    }

    private static double getDoubleInput(String message) {
        while(true) {
            try {
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Invalid input! Enter a number.");
            }
        }
    }

    private static void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(accounts);
            System.out.println("Accounts saved. Exiting...");
        } catch(IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    private static void loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            accounts = (ArrayList<Account>) ois.readObject();
            System.out.println("Accounts loaded successfully!");
        } catch(FileNotFoundException e) {
            System.out.println("No saved accounts found. Starting fresh.");
        } catch(IOException | ClassNotFoundException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
    }
}
