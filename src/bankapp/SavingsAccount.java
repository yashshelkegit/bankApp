package bankapp;
import myexceptions.InsufficientBalanceException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SavingsAccount implements Account{
    double initialBalance;
    String accNo;
    double balance = 0.0;

    List<Double> transactions = new ArrayList<Double>();
    public SavingsAccount(double initialBalance){
        this.accNo = UUID.randomUUID().toString().substring(0, 4);
        this.initialBalance = initialBalance;
        balance = initialBalance;
        System.out.println("Account created : " + accNo);
    }
    @Override
    public void deposit(double amt) {
        transactions.add(amt);
        balance += amt;
    }

    @Override
    public void withdraw(double amt) throws InsufficientBalanceException{
        if((amt > balance)){
            throw new InsufficientBalanceException("Insufficient balance");
        } else{
            transactions.add(-amt);
            balance -= amt;
        }
    }

    @Override
    public double getBalance() {
        return balance;
    }
    public AccountType getType(){
        return AccountType.SAVINGS;
    }
    public String getAccNo(){
        return this.accNo;
    }
    public List getTransactions(){
        return this.transactions;
    }
    public String toString(){
        return "\nBalance - " + balance + " Type - " + getType() + " Acc No : " + accNo;
    }
}
