package exceptions;

public class UnknownOperationException extends RuntimeException {
    public UnknownOperationException(String message) {
        super(message);
    }
}
