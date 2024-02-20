package Exceptions;

public class ClientRegistrationException extends RuntimeException {
    public ClientRegistrationException() {
        super();
    }

    public ClientRegistrationException(String message) {
        super(message);
    }
}
