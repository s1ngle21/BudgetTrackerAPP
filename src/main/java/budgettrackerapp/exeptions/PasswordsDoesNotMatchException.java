package budgettrackerapp.exeptions;

public class PasswordsDoesNotMatchException extends RuntimeException {
    public PasswordsDoesNotMatchException() {
        super();
    }

    public PasswordsDoesNotMatchException(String message) {
        super(message);
    }

    public PasswordsDoesNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
