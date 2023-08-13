package budgettrackerapp.exeptions;

public class ExpenditureInSpecificCategoryDoesNotExistException extends AppException {

    public ExpenditureInSpecificCategoryDoesNotExistException(String message) {
        super("BTS-2", message);
    }

    public ExpenditureInSpecificCategoryDoesNotExistException(String message, Throwable cause) {
        super("BTS-2", message, cause);
    }
}

