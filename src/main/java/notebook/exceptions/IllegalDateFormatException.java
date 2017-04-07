package notebook.exceptions;

public class IllegalDateFormatException extends Exception {
    public IllegalDateFormatException() {
    }

    public IllegalDateFormatException(String message) {
        super(message);
    }
}