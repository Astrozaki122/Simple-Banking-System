import java.io.*;
import java.util.*;

// Account class
class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private int accountNumber;
    private String name;
    private double balance;

    public Account(int accountNumber, String name, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if(amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Invalid amount!");
        }
    }

    public void withdraw(double amount) {
        if(amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
        } else {
            System.out.println("Invalid or insufficient balance!");
        }
    }

    public void transfer(Account target, double amount) {
        if(amount > 0 && amount <= balance) {
            balance -= amount;
            target.deposit(amount);
            System.out.println("Transferred $" + amount + " to " + target.getName());
        } else {
            System.out.println("Invalid transfer amount!");
        }
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber + ", Name: " + name + ", Balance: $" + balance;
    }
}