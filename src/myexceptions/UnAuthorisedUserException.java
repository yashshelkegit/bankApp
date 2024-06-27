package myexceptions;

public class UnAuthorisedUserException extends Exception{
    public UnAuthorisedUserException(String msg){
        super(msg);
    }
}
