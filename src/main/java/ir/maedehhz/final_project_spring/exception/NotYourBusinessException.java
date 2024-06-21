package ir.maedehhz.final_project_spring.exception;

public class NotYourBusinessException extends RuntimeException{
    public NotYourBusinessException(String message) {
        super(message);
    }
}
