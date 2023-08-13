package budgettrackerapp.exeptions;

public class CategoryDoesNotExistException extends AppException {


    public CategoryDoesNotExistException(String message) {
        super("BTS-1", message);
    }

    public CategoryDoesNotExistException(String message, Throwable cause) {
        super("BTS-1", message, cause);
    }
}
