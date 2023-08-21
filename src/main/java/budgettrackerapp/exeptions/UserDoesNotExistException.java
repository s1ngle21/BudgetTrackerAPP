package budgettrackerapp.exeptions;

public class UserDoesNotExistException extends AppException {
    public UserDoesNotExistException(String message) {
        super(ExceptionCode.BTS4, message);
    }

    public UserDoesNotExistException(String message, Throwable cause) {
        super(ExceptionCode.BTS4, message, cause);
    }
}
