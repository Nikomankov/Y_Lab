package Exceptions;

public class IllegalValuesException extends Exception{
    public IllegalValuesException(){
        super("The set values go beyond the boundaries of the playing field. 0 < values < 4");
    }
}
