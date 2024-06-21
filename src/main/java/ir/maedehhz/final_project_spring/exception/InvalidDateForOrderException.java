package ir.maedehhz.final_project_spring.exception;

public class InvalidDateForOrderException extends RuntimeException{
    public InvalidDateForOrderException(String message) {
        super(message);
    }
}
