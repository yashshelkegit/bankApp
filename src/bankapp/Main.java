package bankapp;

import myexceptions.InvalidAccountTypeException;
import myexceptions.UnAuthorisedUserException;

public class Main {

    public static void main(String[] args) {
        int choice;
        do{
            choice = displayMenu();
        }while(choice != 5);
    }
    public static int displayMenu(){
        System.out.println("---------Menu--------");
        System.out.println("1. 🪪Create Account");
        System.out.println("2. Manage Account");
        System.out.println("3. 📋Account Statements");
        System.out.println("4. 💳Money Transaction");
        System.out.println("5. Exit");
        System.out.println("Enter your choice : ");

        int choice = new java.util.Scanner(System.in).nextInt();

        return action(choice);

    }
    public static int action(int choice){
        switch(choice){
            case 1 :
                try{
                    AccountManager.openAccount();
                }catch (InvalidAccountTypeException e){
                    System.out.println(e.getMessage());
                }

                break;
            case 2 :
                try{
                    AccountManager.manageAccount();
                }catch (UnAuthorisedUserException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 3 :
                try{
                    AccountManager.printStatement();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case 4 :
                try{
                    AccountManager.transactMoney();
                }catch(UnAuthorisedUserException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 5 :
                System.out.println(5);
                break;
            default :
                System.out.println("❌ invalid choice");
        }
        return choice;
    }
}