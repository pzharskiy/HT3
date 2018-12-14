package exceptions;

public class InvalidNumberOfArgumentsException extends RuntimeException {
    public InvalidNumberOfArgumentsException(String message) {
        super(message);
    }
}
