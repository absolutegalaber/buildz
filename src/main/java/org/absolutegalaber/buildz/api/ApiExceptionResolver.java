package org.absolutegalaber.buildz.api;

import org.absolutegalaber.buildz.domain.ExceptionInfo;
import org.absolutegalaber.buildz.domain.InvalidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@ControllerAdvice
public class ApiExceptionResolver {

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionInfo handleInvalidInput(InvalidRequestException exc) {
        return new ExceptionInfo(exc.getMessage());
    }
}
