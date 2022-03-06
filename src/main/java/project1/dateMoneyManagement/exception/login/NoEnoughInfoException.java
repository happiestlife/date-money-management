package project1.dateMoneyManagement.exception.login;

public class NoEnoughInfoException extends RuntimeException{
    private static final String msg = "notEnoughInfo";
    public NoEnoughInfoException() {
        super(msg);
    }
}
