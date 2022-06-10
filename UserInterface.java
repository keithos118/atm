import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInterface {
    static final Integer DEFAULT_VALUE = -1;
    static final Integer LOGOUT = -100;
    static final Integer TERMINATE = -101;
    static final Integer INCORRECT_PIN = -200;
    static final Integer INSUFFICIENT_BALANCE = -201;
    static final Integer NOT_AVAILABLE = -300;

    private static User getUser(Database database) {
        Integer accountNo = DEFAULT_VALUE;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Please enter your account number: ");
            accountNo = Integer.parseInt(br.readLine());
        } catch (IOException ioe) {
            System.out.println(ioe);
        }

        User user = database.retrieveUser(accountNo);

        if (user == null) {
            System.out.println("Account not found in database! Please try again");
        }

        return user;
    }

    private static void printWelcomeMessage() {
        System.out.println("Welcome to ATM! Press the appropriate number given below:");
        System.out.println();
        System.out.println("1. Check Balance");
        System.out.println("2. Withdraw Amount");
        System.out.println("3. Get maximum withdrawal amount");
        System.out.println("4. Repeat this message");
        System.out.println("5. Logout");
        System.out.println("6. Terminate Application");
    }

    private static Integer processOperation(User user, ATM atm) {

        Integer operation = DEFAULT_VALUE;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println();
            System.out.print("Enter the operation you wish to perform: ");
            operation = Integer.parseInt(br.readLine());
        } catch (IOException ioe) {
            System.out.println(ioe);
        }

        Integer balance = DEFAULT_VALUE;
        Integer enteredPin = DEFAULT_VALUE;
        Integer requestedAmount = DEFAULT_VALUE;

        switch (operation) {

            case 1:

            try {
                System.out.print("Enter your pin: ");
                enteredPin = Integer.parseInt(br.readLine());
            } catch (IOException ioe) {
                System.out.println(ioe);
            }

            balance = user.getBalance(enteredPin);
            if (balance.equals(INCORRECT_PIN)) {
                System.out.println("Incorrect Pin!!");
            }
            else {
                System.out.println("Your balance: " + String.valueOf(balance));
            }

            break;


            case 2:

            try {
                System.out.print("Enter your pin: ");
                enteredPin = Integer.parseInt(br.readLine());
                System.out.print("Enter the amount you would like to withdraw: ");
                requestedAmount = Integer.parseInt(br.readLine());
            } catch (IOException ioe) {
                System.out.println(ioe);
            }

            Integer balanceCheck = user.checkBalance(enteredPin, requestedAmount);

            if (balanceCheck.equals(INCORRECT_PIN)) {
                System.out.println("Incorrect Pin!!");
                break;
            }

            if (balanceCheck.equals(INSUFFICIENT_BALANCE)) {
                System.out.println("Insufficient Balance!!");
                break;
            }

            assert(balanceCheck.equals(requestedAmount));

            if (!atm.checkWithdrawAmount(requestedAmount)) {
                System.out.println("Requested amount cannot be withdrawn at this time");
                break;
            }

            Integer[] denominations = atm.dispenseAmount(requestedAmount);
            balance = user.deduct(enteredPin, requestedAmount);

            System.out.println("The following notes have been dispensed: " + 
                String.valueOf(denominations[0]) + " 50€s, " + 
                String.valueOf(denominations[1]) + " 20€s, " + 
                String.valueOf(denominations[2]) + " 10€s, " + 
                String.valueOf(denominations[3]) + " 5€s");
            System.out.println("Remaining Balance: " + String.valueOf(balance));

            break;


            case 3:

            System.out.println("Maxmimum Withdrawal Amount: " + String.valueOf(atm.getTotalAmount()));

            break;


            case 4:

            printWelcomeMessage();

            break;


            case 5:

            return LOGOUT;


            case 6:

            return TERMINATE;


            default:
            System.out.println("Invalid Operation!!");
        }

        return DEFAULT_VALUE;
    }

    public static void main(String[] args) {
        Database database = new Database();
        ATM atm = new ATM();

        User user;
        Integer status = DEFAULT_VALUE;
        do {
            user = getUser(database);
            if (user == null) {
                continue;
            }

            printWelcomeMessage();

            do {
                status = processOperation(user, atm);
            } while (status != LOGOUT && status != TERMINATE);

        } while (status != TERMINATE);
    }

}