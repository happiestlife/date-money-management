package project1.dateMoneyManagement.exception.login;

public class WrongIdOrPasswordException extends RuntimeException{
    private static final String msg = "loginError";
    public WrongIdOrPasswordException() {
        super(msg);
    }
}
