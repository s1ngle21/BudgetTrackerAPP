package budgettrackerapp.exeptions;

public class CategoryDoesNotExistException extends AppException {


    public CategoryDoesNotExistException(String message) {
        super(ExceptionCode.BTS1, message);
    }

    public CategoryDoesNotExistException(String message, Throwable cause) {
        super(ExceptionCode.BTS1, message, cause);
    }
}
