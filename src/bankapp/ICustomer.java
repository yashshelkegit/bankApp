package bankapp;

public interface ICustomer {
    void openAccount(String name, String password, double initialBalance, AccountType type);
    void getUserInfo(String id);
}
