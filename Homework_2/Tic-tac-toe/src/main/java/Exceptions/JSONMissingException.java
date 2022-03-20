package Exceptions;

public class JSONMissingException extends Exception{
        public JSONMissingException() {
            super("JSON file is missing");
        }
}
