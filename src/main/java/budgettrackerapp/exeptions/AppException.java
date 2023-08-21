package budgettrackerapp.exeptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {
    private ExceptionCode errorCode;
    public AppException(ExceptionCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public AppException(ExceptionCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
