package budgettrackerapp.exeptions;

public class ExpenditureInSpecificCategoryDoesNotExistException extends AppException {

    public ExpenditureInSpecificCategoryDoesNotExistException(String message) {
        super(ExceptionCode.BTS2, message);
    }

    public ExpenditureInSpecificCategoryDoesNotExistException(String message, Throwable cause) {
        super(ExceptionCode.BTS2, message, cause);
    }
}

