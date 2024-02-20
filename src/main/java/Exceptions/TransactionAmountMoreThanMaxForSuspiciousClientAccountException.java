package Exceptions;

public class TransactionAmountMoreThanMaxForSuspiciousClientAccountException extends RuntimeException{
    public TransactionAmountMoreThanMaxForSuspiciousClientAccountException() {
        super();
    }

    public TransactionAmountMoreThanMaxForSuspiciousClientAccountException(String message) {
        super(message);
    }
}
