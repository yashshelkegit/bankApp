package bankapp;

import myexceptions.InsufficientBalanceException;
import myexceptions.InvalidAccountTypeException;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

class Customer {
    String name;
    String password;
    String id;
    List<Account> accounts = new ArrayList<>();

    public Customer(String name, String password, Account acc){

        this.name = name;
        this.password = password;
        id = "" + name + password;
        accounts.add(acc);
    }
    public void addAccount(Account acc){
        accounts.add(acc);
    }
    public String getId(){
        return this.id;
    }
    public String toString(){
        return " name - " + name + " Password : " + password + " Accounts - " + accounts;
    }
}

public class CustomerManager implements ICustomer{
    static Scanner sc = new Scanner(System.in);
    static Map<String, String> users = new HashMap<>();
    static Map<String, Customer> customers = new HashMap<>();
    Set<Account> accountSet = new HashSet<Account>();
    Account account;
    @Override
    public void openAccount(String name, String password, double initialBalance, AccountType accType){
        if(users.containsKey(name)){
            System.out.println("🙅User Already Exixts !!!");
            return;
        }
        users.put(name, password);
        Customer c = null;
        switch(accType){
            case SAVINGS :
                account = new SavingsAccount(initialBalance);
                accountSet.add(account);
                c = new Customer(name, password, account);
                break;
            case CURRENT :
                account = new CurrentAccount(initialBalance);
                accountSet.add(account);
                c = new Customer(name, password, account);
                break;
            case LOAN :
                System.out.println("loan");
                break;
            default :
                System.out.println("❌Invalid account type");
        }
        customers.put(c.getId(), c);
        System.out.println(accountSet);
    }

    @Override
    public void getUserInfo(String id) {
        System.out.println(customers.get(id));
    }
    public static boolean authenticate(){
        System.out.println("👨‍💼Enter Username :");
        String username = sc.nextLine();
        System.out.println("🔑Enter Password :");
        String password = sc.nextLine();

        if(users.containsKey(username) && password.equals(users.get(username))){
            return true;
        }
        else{
            return false;
        }
    }
    public void listAccounts(String id){
        Customer c = customers.get(id);
        if(c != null){
            if(c.accounts.size() == 0 ){
                System.out.println("you don't Have any Account !!!");
                return;
            }
            c.accounts.forEach(acc -> System.out.println(acc));
        }
        else System.out.println("❌Wrong Account No. !!!");
    }
    public void deleteAccount(String id){
        Customer c = customers.get(id);
        System.out.println("Enter Account No to delete :");
        String acn = sc.next();
        c.accounts.stream().filter(acc -> acc.getAccNo() != acn);
        System.out.println("Account deleted Successfully");

    }
    public void deleteAllAccount(String id){
        Customer c = customers.get(id);
        c.accounts.clear();
        System.out.println("All Accounts deleted Successfully");
    }

    public void AddAccount(String id) throws InvalidAccountTypeException {
        Customer c = customers.get(id);
        AccountType accType;
        System.out.println("-------Select Account Type------");
        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");
        System.out.println("3. Loan Acount");
        System.out.println("Enter the type of account you want to create : ");
        int type = sc.nextInt();
        System.out.println("Enter Initial Balance :");
        double ib = sc.nextDouble();
        switch (type){
            case 1 :
                accType = AccountType.SAVINGS;
                Account saveAcc = new SavingsAccount(ib);
                c.accounts.add(saveAcc);
                accountSet.add(saveAcc);
                break;
            case 2 :
                accType = AccountType.CURRENT;
                Account currAcc = new CurrentAccount(ib);
                c.accounts.add(currAcc);
                accountSet.add(currAcc);
                break;
            case 3 : accType = AccountType.LOAN; break;
            default: throw new InvalidAccountTypeException("Invalid Type");

        }
        System.out.println(accountSet);
    }
    public void deposit(String acn){
        try {
            System.out.println("Enter Amount 💵 to deposit");
            double amt = sc.nextDouble();
            Account acc = null;
            Iterator itr = accountSet.iterator();
            while(itr.hasNext()){
                Account ac = (Account) itr.next();
                if((ac.getAccNo().equals(acn))){
                    acc = ac;
                    break;
                }
            }
            acc.deposit(amt);
            System.out.println("Rs. " + amt + " deposited successfully...✅");
        }catch(NullPointerException e){
            System.out.println("Wrong Account no. ❌");
        }
    }

    public void withdraw(String acn){
        System.out.println("Enter Amount 💷 to withdraw ");
        double amt = sc.nextDouble();
        Account acc = null;
        Iterator itr = accountSet.iterator();
        while(itr.hasNext()){
            Account ac = (Account) itr.next();
            System.out.println((ac.getAccNo().equals(acn)));
            if((ac.getAccNo().equals(acn))){
                acc = ac;
                break;
            }
        }
        try{
            acc.withdraw(amt);
            System.out.println("Rs. " + amt + " withdrawed successfully...✅");
        }catch(InsufficientBalanceException e){
            System.out.println(e.getMessage());
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    public void printStatement(String acn){
        Account acc = null;
        Iterator itr = accountSet.iterator();
        while(itr.hasNext()){
            Account ac = (Account) itr.next();
            if((ac.getAccNo().equals(acn))){
                acc = ac;
                break;
            }
        }
        System.out.println(acc.getTransactions());
        for(var t : acc.getTransactions()){
            if((Double)t > 0){
                System.out.println("📈Deposit : " + t);
            }
            if((Double) t < 0)
                System.out.println("📉Withdraw : " + t);
        }
        generateStatement(acc);
        System.out.println("Total Balance 💶: "+ acc.getBalance());
    }

    public void generateStatement(Account acc){
        try{
            PrintWriter pw = new PrintWriter(new FileOutputStream("account-statement.txt"));
            for(var t : acc.getTransactions()){
                if((Double)t > 0){
                    pw.println("📈Deposit : " + t);
                }
                if((Double) t < 0)
                    pw.println("📉Withdraw : " + t);
            }
            pw.println("Total Balance 💶: "+ acc.getBalance());
            pw.close();
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(new File("account-statement.txt"));
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }

    }
}
