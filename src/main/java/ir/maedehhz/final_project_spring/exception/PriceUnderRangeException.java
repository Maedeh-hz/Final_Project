package ir.maedehhz.final_project_spring.exception;

public class PriceUnderRangeException extends RuntimeException{
    public PriceUnderRangeException(String message) {
        super(message);
    }
}
