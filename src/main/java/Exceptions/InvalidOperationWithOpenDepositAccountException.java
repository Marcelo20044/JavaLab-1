package Exceptions;

public class InvalidOperationWithOpenDepositAccountException extends RuntimeException {
    public InvalidOperationWithOpenDepositAccountException() {
        super();
    }

    public InvalidOperationWithOpenDepositAccountException(String msg) {
        super(msg);
    }
}
