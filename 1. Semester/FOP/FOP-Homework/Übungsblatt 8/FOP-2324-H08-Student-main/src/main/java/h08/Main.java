package h08;

import org.tudalgo.algoutils.student.Student;
import org.tudalgo.algoutils.student.test.StudentTestUtils;

import java.time.LocalDate;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Epsilon for comparing double values.
     */
    private static final double EPSILON = 0.0001;

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {
        Customer customer1 = new Customer("Max", "Mustermann", "Frankfurt", LocalDate.of(2001,6, 25));
        Customer customer2 = new Customer("Alex", "Borodin", "Darmstadt", LocalDate.of(2000,4, 8));
        Bank bank1 = new Bank("Sparkasse", 42);
        Bank bank2 = new Bank("Comdirect", 123);
        bank1.add(bank2);
        bank1.add(customer1);
        bank2.add(bank1);
        bank2.add(customer2);

        Account accountReference1 = bank1.getAccounts()[0];
        Account accountReference2 = bank2.getAccounts()[0];

        bank1.deposit(accountReference1.getIban(), 20000);
        bank2.deposit(accountReference2.getIban(), 20000);

        try {
            bank1.transfer(accountReference1.getIban(), accountReference2.getIban(), accountReference2.getBank().getBic(), 1000, "Testüberweisung");
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        }

        StudentTestUtils.testWithinRange(19000-EPSILON, 19000+EPSILON, accountReference1.getBalance());
        StudentTestUtils.testWithinRange(21000-EPSILON, 21000+EPSILON, accountReference2.getBalance());
        StudentTestUtils.testEquals(accountReference1.getHistory(), accountReference2.getHistory());

        try {
            bank1.transfer(accountReference1.getIban(), accountReference2.getIban(), 444, 1000, "Testüberweisung");
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        }

        StudentTestUtils.testEquals(accountReference1.getHistory(), accountReference2.getHistory());

    }

}
