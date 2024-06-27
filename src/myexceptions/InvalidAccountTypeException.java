package myexceptions;

public class InvalidAccountTypeException extends Exception{
    public InvalidAccountTypeException(String msg){
        super(msg);
    }
}
