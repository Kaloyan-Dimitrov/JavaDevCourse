import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
// Concurrent challenges 1-6
public class BankAccount {
    private double balance;
    private String accountNumber;

    private Lock lock = new ReentrantLock();

    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        boolean status = false;
        try {
            if(lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                try {
                    balance += amount;
                    status = true;
                } finally {
                    lock.unlock();
                }
            } else System.out.println("Could not get the lock");
        } catch (InterruptedException e) {
            //
        }
        System.out.println("Transaction status: " + status);
    }

    public synchronized void withdraw(double amount) {
        boolean status = false;
        try {
            if(lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                try {
                    balance -= amount;
                    status = true;
                } finally {
                    lock.unlock();
                }
            } else System.out.println("Could not get the lock");
        } catch (InterruptedException e) {
            //
        }
        System.out.println("Transaction status: " + status);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void printAccountNumber() {
        System.out.println("Account Number = " + this.accountNumber);
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount("12345-678", 1000.00);
        Thread person1Thread = new Thread(new Runnable() {
            @Override
            public void run() {
                account.deposit(300);
                account.withdraw(50);
            }
        });
        Thread person2Thread = new Thread(new Runnable() {
            @Override
            public void run() {
                account.deposit(203.75);
                account.withdraw(100);
            }
        });
        person1Thread.start();
        person2Thread.start();
        while(!(person1Thread.getState() == Thread.State.TERMINATED
            && person1Thread.getState() == Thread.State.TERMINATED)){}
        System.out.println(account.getBalance());
    }
}
