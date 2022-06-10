import org.junit.Test;
import junit.framework.TestCase;
import static org.junit.Assert.*;

public class UnitTest extends TestCase {
    static final Integer DEFAULT_VALUE = -1;
    static final Integer LOGOUT = -100;
    static final Integer TERMINATE = -101;
    static final Integer INCORRECT_PIN = -200;
    static final Integer INSUFFICIENT_BALANCE = -201;
    static final Integer NOT_AVAILABLE = -300;

    @Test
    public void testBalance() {
        Database database = new Database();
        User user1 = database.retrieveUser(123456789);
        User user2 = database.retrieveUser(987654321);

        assertEquals(user1.getBalance(1234).intValue(), 800);
        assertEquals(user2.getBalance(4321).intValue(), 1230);
        assertEquals(user1.getBalance(1235).intValue(), INCORRECT_PIN.intValue());
        assertEquals(user2.getBalance(5321).intValue(), INCORRECT_PIN.intValue());
    }

    @Test
    public void testCheckBalance() {
        Database database = new Database();
        User user1 = database.retrieveUser(123456789);
        User user2 = database.retrieveUser(987654321);

        assertEquals(user1.checkBalance(1234, 1000).intValue(), INSUFFICIENT_BALANCE.intValue());
        assertEquals(user1.checkBalance(1234, 400).intValue(), 400);
        assertEquals(user2.checkBalance(4321, 1500).intValue(), INSUFFICIENT_BALANCE.intValue());
        assertEquals(user2.checkBalance(4321, 1000).intValue(), 1000);
    }

    @Test
    public void testDeduct() {
        Database database = new Database();
        User user1 = database.retrieveUser(123456789);
        User user2 = database.retrieveUser(987654321);

        assertEquals(user1.deduct(1234, 300).intValue(), 500);
        assertEquals(user2.deduct(4321, 1000).intValue(), 230);
    }

    @Test
    public void testDispense() {
        ATM atm = new ATM();

        Integer[] answer1 = {10,6,1,1};
        Integer[] answer2 = {0,8,0,1};

        assertArrayEquals(atm.dispenseAmount(635), answer1);
        assertArrayEquals(atm.dispenseAmount(165), answer2);
    }

    @Test
    public void testTotalAmount() {
        ATM atm = new ATM();

        assertEquals(atm.getTotalAmount().intValue(), 1500);
        atm.dispenseAmount(635);
        assertEquals(atm.getTotalAmount().intValue(), 865);
    }

    @Test
    public void testCheckWithdrawAmount() {
        ATM atm = new ATM();

        assertTrue(atm.checkWithdrawAmount(635));
        assertTrue(atm.checkWithdrawAmount(1500));
        assertFalse(atm.checkWithdrawAmount(1600));
        assertFalse(atm.checkWithdrawAmount(636));
    }

}