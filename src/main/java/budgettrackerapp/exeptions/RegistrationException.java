package budgettrackerapp.exeptions;

public class RegistrationException extends AppException {

    public RegistrationException(String message) {
        super(ExceptionCode.BTS3, message);
    }

    public RegistrationException(String message, Throwable cause) {
        super(ExceptionCode.BTS3, message, cause);
    }
}
