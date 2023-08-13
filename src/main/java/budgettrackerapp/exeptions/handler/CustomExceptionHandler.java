package budgettrackerapp.exeptions.handler;

import budgettrackerapp.exeptions.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(ExpenditureInSpecificCategoryDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleExpenditureInSpecificCategoryDoesNotExistException(ExpenditureInSpecificCategoryDoesNotExistException e) {
        log.error(e.getMessage(), e);
    return new ErrorResponse(e.getErrorCode(), e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNullPointerException(NullPointerException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(CategoryDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCategoryDoesNotExistException(CategoryDoesNotExistException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getErrorCode(), e.getMessage(), HttpStatus.NOT_FOUND.value());
    }


    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserWithCurrentNameAlreadyExistException(UsernameAlreadyExistsException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getErrorCode(), e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(RegistrationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRegistrationException(RegistrationException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getErrorCode(), e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

}
