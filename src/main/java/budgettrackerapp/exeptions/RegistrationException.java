package budgettrackerapp.exeptions;

public class RegistrationException extends AppException {

    public RegistrationException(String message) {
        super("BTS-3", message);
    }

    public RegistrationException(String message, Throwable cause) {
        super("BTS-3", message, cause);
    }
}
