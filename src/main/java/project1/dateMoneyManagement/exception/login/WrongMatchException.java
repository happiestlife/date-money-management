package project1.dateMoneyManagement.exception.login;

public class WrongMatchException extends RuntimeException{
    private static String msg;
    public WrongMatchException(String msg) {
        super(msg);
    }
}
