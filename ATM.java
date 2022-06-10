public class ATM {
    static final Integer DEFAULT_VALUE = -1;
    static final Integer NOT_AVAILABLE = -300;

    Integer numFifty;
    Integer numTwenty;
    Integer numTen;
    Integer numFive;

    private Integer[] minimumNotes(Integer amount) {
        Integer[] denominations = new Integer[4];

        if (amount / 50 >= numFifty) {
            denominations[0] = numFifty;
            amount -= numFifty * 50;
        }
        else {
            denominations[0] = amount / 50;
            amount = amount % 50;
        }
        
        if (amount / 20 >= numTwenty) {
            denominations[1] = numTwenty;
            amount -= numTwenty * 20;
        }
        else {
            denominations[1] = amount / 20;
            amount = amount % 20;
        }
        
        if (amount / 10 >= numTen) {
            denominations[2] = numTen;
            amount -= numTen * 10;
        }
        else {
            denominations[2] = amount / 10;
            amount = amount % 10;
        }
        
        if (amount / 5 >= numFive) {
            denominations[3] = numFive;
            amount -= numFive * 5;
        }
        else {
            denominations[3] = amount / 5;
            amount = amount % 5;
        }

        if (!amount.equals(0)) {
            denominations[0] = denominations[1] = denominations[2] = denominations[3] = DEFAULT_VALUE;
        }

        return denominations;
    }

    public ATM() {
        System.out.print("Initialising ATM cash ... ");

        numFifty = 10;
        numTwenty = 30;
        numTen = 30;
        numFive = 20;

        System.out.println("Done!");
    }

    public Integer getTotalAmount() {
        return numFifty * 50 + numTwenty * 20 + numTen * 10 + numFive * 5;
    }

    public boolean checkWithdrawAmount (Integer amount) {
        if (amount > getTotalAmount()) {
            return false;
        }

        Integer[] denominations = minimumNotes(amount);
        if (denominations[0].equals(DEFAULT_VALUE)) {
            return false;
        }

        return true;
    }

    public Integer[] dispenseAmount (Integer amount) {
        assert(checkWithdrawAmount(amount));

        Integer[] denominations = minimumNotes(amount);

        numFifty -= denominations[0];
        numTwenty -= denominations[1];
        numTen -= denominations[2];
        numFive -= denominations[3];

        return denominations;
    }
    
}
