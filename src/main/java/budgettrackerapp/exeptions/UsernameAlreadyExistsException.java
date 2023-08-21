package budgettrackerapp.exeptions;

public class UsernameAlreadyExistsException extends AppException {

    public UsernameAlreadyExistsException(String message) {
        super(ExceptionCode.BTS5, message);
    }

    public UsernameAlreadyExistsException(String message, Throwable cause) {
        super(ExceptionCode.BTS5, message, cause);
    }
}
