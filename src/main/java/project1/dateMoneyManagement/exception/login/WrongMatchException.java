package project1.dateMoneyManagement.exception.login;

public class WrongMatchException extends RuntimeException{
//    private final static String msg = "notMatchPasswordWithCheck";
    public WrongMatchException(String msg) {
        super(msg);
    }
}
