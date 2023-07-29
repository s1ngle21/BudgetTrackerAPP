package budgettrackerapp.exeptions.handler;

import budgettrackerapp.exeptions.CategoryDoesNotExistException;
import budgettrackerapp.exeptions.ExpenditureInSpecificCategoryDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

}
