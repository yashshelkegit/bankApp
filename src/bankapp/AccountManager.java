package bankapp;
import java.util.List;
import java.util.Scanner;
import myexceptions.*;

interface Account {
    void deposit(double amt);
    void withdraw(double amt) throws InsufficientBalanceException;
    double getBalance();
    String getAccNo();
    List getTransactions();
    AccountType getType();
}
public class AccountManager {
    static Scanner sc = new Scanner(System.in);
    static CustomerManager cm = new CustomerManager();
    public static void openAccount() throws InvalidAccountTypeException{
        AccountType accType;
        System.out.println("------Select Account Type------");
        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");
        System.out.println("3. Loan Acount");
        System.out.println("Enter the type of account you want to create : ");
        int type = sc.nextInt();
        switch (type){
            case 1 : accType = AccountType.SAVINGS; break;
            case 2 : accType = AccountType.CURRENT; break;
            case 3 : accType = AccountType.LOAN; break;
            default: throw new InvalidAccountTypeException("❌ Invalid Type");

        }
        String name, password;
        System.out.println("Enter your name :");
        name = sc.next();
        System.out.println("Enter your password :");
        password = sc.next();
        System.out.println("Enter Initial Balance :");
        double ib = sc.nextDouble();

        cm.openAccount(name, password, ib, accType);

    }
    public static void manageAccount() throws UnAuthorisedUserException{
        if(cm.authenticate()){
            System.out.println("------Management Menu 🗄️------");
            System.out.println("1. Your Accounts");
            System.out.println("2. Delete Your Account");
            System.out.println("3. Delete All Accounts");
            System.out.println("4. Add Account");
            System.out.println("5. ⬅️Go back");
            System.out.println("enter your choice");
            int choice = sc.nextInt();
            process(choice);
        }else{
            throw new UnAuthorisedUserException("❌ Invalid user");
        }
    }

    public static void process(int choice){
        System.out.println("Enter 🔑Customer ID.");
        String id = sc.next();
        switch(choice){
            case 1 : cm.listAccounts(id); break;
            case 2 : cm.deleteAccount(id); break;
            case 3 : cm.deleteAllAccount(id); break;
            case 4 :
                try{
                    cm.AddAccount(id);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case 5 : Main.main(new String[]{}); break;
            default :
                System.out.println("❌ invalid choice");
        }
    }
    public static void transactMoney() throws UnAuthorisedUserException{
        if(cm.authenticate()){
            System.out.println("----------------");
            System.out.println("1. Deposit ⤵️");
            System.out.println("2. Withdraw ⤴️");
            System.out.println("enter your choice :");
            int choice = sc.nextInt();
            System.out.println("Enter Account no. 🔐:");
            String acn = sc.next();
            switch (choice) {
                case 1 -> cm.deposit(acn);
                case 2 -> cm.withdraw(acn);
                default -> System.out.println("❌ Invalid choice");
            }
        }else{
            throw new UnAuthorisedUserException("❌ Invalid user");
        }
    }
    public static void printStatement(){
        System.out.println("Enter your Account no.🔐 :");
        String acn = sc.next();
        cm.printStatement(acn);
    }

}
