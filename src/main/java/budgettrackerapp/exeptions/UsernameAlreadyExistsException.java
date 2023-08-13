package budgettrackerapp.exeptions;

public class UsernameAlreadyExistsException extends AppException {

    public UsernameAlreadyExistsException(String message) {
        super("BTS-5", message);
    }

    public UsernameAlreadyExistsException(String message, Throwable cause) {
        super("BTS-5", message, cause);
    }
}
