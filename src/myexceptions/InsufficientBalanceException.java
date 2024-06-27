package myexceptions;

import javax.naming.InterruptedNamingException;

public class InsufficientBalanceException extends Exception{
    public InsufficientBalanceException(String msg){
        super(msg);
    }

}
