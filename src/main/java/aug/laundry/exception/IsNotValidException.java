package aug.laundry.exception;

public class IsNotValidException extends RuntimeException{

    public IsNotValidException() {
        super();
    }

    public IsNotValidException(String message) {
        super(message);
    }

    public IsNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public IsNotValidException(Throwable cause) {
        super(cause);
    }

    protected IsNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
