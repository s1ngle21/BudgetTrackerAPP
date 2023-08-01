package budgettrackerapp.exeptions.handler;

import budgettrackerapp.exeptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class CustomExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(ExpenditureInSpecificCategoryDoesNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleExpenditureInSpecificCategoryDoesNotExistException(ExpenditureInSpecificCategoryDoesNotExistException e) {
        LOGGER.error(e.getMessage(), e);
    return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNullPointerException(NullPointerException e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(CategoryDoesNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleCategoryDoesNotExistException(CategoryDoesNotExistException e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse("Can not find parameter to execute this method", HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(PasswordsDoesNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlePasswordsDoesNotMatchException( PasswordsDoesNotMatchException e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(UserWithCurrentNameAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserWithCurrentNameAlreadyExistException(UserWithCurrentNameAlreadyExistException e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

}
