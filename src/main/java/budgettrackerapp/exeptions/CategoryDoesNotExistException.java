package budgettrackerapp.exeptions;

public class CategoryDoesNotExistException extends RuntimeException {
    public CategoryDoesNotExistException() {
        super();
    }

    public CategoryDoesNotExistException(String message) {
        super(message);
    }

    public CategoryDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
