package budgettrackerapp.exeptions;

public class UserWithCurrentNameAlreadyExistException extends RuntimeException {
    public UserWithCurrentNameAlreadyExistException() {
        super();
    }

    public UserWithCurrentNameAlreadyExistException(String message) {
        super(message);
    }

    public UserWithCurrentNameAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
