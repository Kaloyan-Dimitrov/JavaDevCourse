import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrencyChallenge7 {
    public static void main(String[] args) {
        NewBankAccount1 account1 = new NewBankAccount1("12345-678", 500.00);
        NewBankAccount1 account2 = new NewBankAccount1("98765-432", 1000.00);

        new Thread(new Transfer(account1, account2, 10.00),
                "Transfer1 (acc1-acc2)").start();
        new Thread(new Transfer(account2, account1, 55.88),
                "Transfer2 (acc2-acc1)").start();
    }
}

class NewBankAccount1 {
    private double balance;
    private String accountNumber;
    private Lock lock = new ReentrantLock();

    NewBankAccount1(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public boolean withdraw(double amount) {
        if (lock.tryLock()) {
            System.out.printf("%s: Starting withdraw and locking lock\n", Thread.currentThread().getName());

            try {
                try {
                    // Simulate database access
                    System.out.printf("%s: Sleeps\n", Thread.currentThread().getName());
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                }
                balance -= amount;
                System.out.printf("%s: Withdrew %f\n", Thread.currentThread().getName(), amount);
                return true;
            } finally {
                System.out.printf("%s: Unlocking lock.\n", Thread.currentThread().getName());
                lock.unlock();
            }
        }
        return false;
    }

    public boolean deposit(double amount) {
        if (lock.tryLock()) {
            System.out.printf("%s: Starting deposit and locking lock\n", Thread.currentThread().getName());
            try {
                try {
                    // Simulate database access
                    System.out.printf("%s: Sleeps\n", Thread.currentThread().getName());
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                }
                balance += amount;
                System.out.printf("%s: Deposited %f\n", Thread.currentThread().getName(), amount);
                return true;
            } finally {
                System.out.printf("%s: Unlocking lock.\n", Thread.currentThread().getName());
                lock.unlock();
            }
        }
        return false;
    }

    public boolean transfer(NewBankAccount1 destinationAccount, double amount) {
        if (withdraw(amount)) {
            if (destinationAccount.deposit(amount)) {
                return true;
            }
            else {
                // The deposit failed. Refund the money back into the account.
                System.out.printf("%s: Destination account busy. Refunding money\n",
                        Thread.currentThread().getName());
                deposit(amount);
            }
        }

        return false;
    }
}

class Transfer implements Runnable {
    private NewBankAccount1 sourceAccount, destinationAccount;
    private double amount;

    Transfer(NewBankAccount1 sourceAccount, NewBankAccount1 destinationAccount, double amount) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public void run() {
        System.out.printf("%s: Starting transfer\n", Thread.currentThread().getName());
        while (!sourceAccount.transfer(destinationAccount, amount)) {}
        System.out.printf("%s completed\n", Thread.currentThread().getName());
    }
}
