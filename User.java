public class User {
    static final Integer INCORRECT_PIN = -200;
    static final Integer INSUFFICIENT_BALANCE = -201;

    Integer accountNo;
    Integer pin;
    Integer balance;
    Integer overdraft;

    public User (Integer accountNo, Integer pin, Integer balance, Integer overdraft) {
        this.accountNo = accountNo;
        this.pin = pin;
        this.balance = balance;
        this.overdraft = overdraft;
    }

    private void debit (Integer amount) {
        this.balance -= amount;
    }

    public Integer getBalance (Integer pin) {
        if (!this.pin.equals(pin)) {
            System.out.println(String.valueOf(this.pin) + " " + String.valueOf(pin));
            return INCORRECT_PIN;
        }

        return this.balance;
    }

    public Integer checkBalance (Integer pin, Integer amount) {
        if (!this.pin.equals(pin)) {
            return INCORRECT_PIN;
        }

        if (amount > balance) {
            return INSUFFICIENT_BALANCE;
        }

        return amount;
    }

    public Integer deduct (Integer pin, Integer amount) {
        Integer balanceCheck = checkBalance(pin, amount);

        if (balanceCheck.equals(INCORRECT_PIN) || balanceCheck.equals(INSUFFICIENT_BALANCE)) {
            return balanceCheck;
        }

        debit(amount);

        return getBalance(pin);
    }
}
