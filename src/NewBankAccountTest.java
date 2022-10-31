import static org.junit.jupiter.api.Assertions.*;

class NewBankAccountTest {

    @org.junit.jupiter.api.Test
    void deposit() {
        NewBankAccount bankAccount = new NewBankAccount("Kaloyan", "Dimitrov", 1000);
        double balance = bankAccount.deposit(200, true);
        assertEquals(1200, balance, 0);
    }

    @org.junit.jupiter.api.Test
    void withdraw() {
        fail("This test has yet to be implemented.");
    }

    @org.junit.jupiter.api.Test
    void getBalance() {
        fail("This test has yet to be implemented.");
    }
}