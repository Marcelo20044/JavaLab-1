package Exceptions;

public class OutOfCreditLimitException extends IllegalArgumentException {
    public OutOfCreditLimitException() {
        super();
    }

    public OutOfCreditLimitException(String s) {
        super(s);
    }
}
