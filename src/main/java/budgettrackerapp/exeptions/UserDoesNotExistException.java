package budgettrackerapp.exeptions;

public class UserDoesNotExistException extends AppException {
    public UserDoesNotExistException(String message) {
        super("BTS-4", message);
    }

    public UserDoesNotExistException(String message, Throwable cause) {
        super("BTS-4", message, cause);
    }
}
