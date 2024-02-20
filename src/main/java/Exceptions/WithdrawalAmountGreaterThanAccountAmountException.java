package Exceptions;

public class WithdrawalAmountGreaterThanAccountAmountException extends IllegalArgumentException {
    public WithdrawalAmountGreaterThanAccountAmountException() {
        super();
    }

    public WithdrawalAmountGreaterThanAccountAmountException(String s) {
        super(s);
    }
}
