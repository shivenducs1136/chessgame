package exceptions;

public class PositionSizeTooShortException extends RuntimeException{
    public PositionSizeTooShortException(){
        super("Position size is too short it should be size exactly equals to 2");
    }
}
