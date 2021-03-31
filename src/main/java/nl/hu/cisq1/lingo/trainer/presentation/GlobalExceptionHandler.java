package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundIsNotFinishedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity handleException(GameNotFoundException exc) {
        logger.error("GameNotFoundException caught", exc);
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(RoundIsNotFinishedException.class)
    public ResponseEntity handleException(RoundIsNotFinishedException exc) {
        logger.error("RoundIsNotFinishedException caught", exc);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception exc) {
        logger.error("Exception caught", exc);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
