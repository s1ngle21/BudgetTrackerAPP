package budgettrackerapp.exeptions;

public class ExpenditureInSpecificCategoryDoesNotExistException extends RuntimeException {
    public ExpenditureInSpecificCategoryDoesNotExistException() {
        super();
    }

    public ExpenditureInSpecificCategoryDoesNotExistException(String message) {
        super(message);
    }

    public ExpenditureInSpecificCategoryDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
