package ir.maedehhz.final_project_spring.exceptionhandler;

import ir.maedehhz.final_project_spring.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundException(NotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CouldNotUpdateException.class)
    public ResponseEntity<Object> couldNotUpdateException(CouldNotUpdateException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateMismatchException.class)
    public ResponseEntity<Object> dateMismatchException(DateMismatchException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateInfoException.class)
    public ResponseEntity<Object> duplicateInfoException(DuplicateInfoException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.MULTIPLE_CHOICES);
    }

    @ExceptionHandler(ImageFormatException.class)
    public ResponseEntity<Object> imageFormatException(ImageFormatException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ImageLengthOutOfBoundException.class)
    public ResponseEntity<Object> imageLengthOutOfBoundException(ImageLengthOutOfBoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Object> invalidInputException(InvalidInputException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Object> invalidRequestException(InvalidRequestException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<Object> passwordMismatchExceptionException(PasswordMismatchException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnverifiedExpertException.class)
    public ResponseEntity<Object> unverifiedExpertException(UnverifiedExpertException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }
}
