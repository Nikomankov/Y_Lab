package Exceptions;

public class XMLMissingException extends Exception{
    public XMLMissingException() {
        super("XML file is missing");
    }
}
