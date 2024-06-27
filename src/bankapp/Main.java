package bankapp/Main;
public class Main {

    public static void main(String[] args) {
        int choice;
        do{
            choice = displayMenu();
        }while(choice != 5);
    }
    public static int displayMenu(){
        System.out.println("---------Menu--------");
        System.out.println("1. Create Account");
        System.out.println("2. Manage Account");
        System.out.println("3. Account Statements");
        System.out.println("4. Money Transaction");
        System.out.println("5. Exit");
        System.out.println("Enter your choice");

        int choice = new java.util.Scanner(System.in).nextInt();

        return action(choice);

    }
    public static int action(int choice){
        switch(choice){
            case 1 :
                System.out.println(1);
                break;
            case 2 :
                System.out.println(2);
                break;
            case 3 :
                System.out.println(3);
                break;
            case 4 :
                System.out.println(4);
                break;
            case 5 :
                System.out.println(5);
                break;
            default :
                System.out.println("invalid choice");
        }
        return choice;
    }
}