package Exceptions;

public class IllegalPositionException extends Exception{
    public IllegalPositionException(){
        super("This position is taken, please select another");
    }
}

