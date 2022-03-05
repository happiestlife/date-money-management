package project1.dateMoneyManagement.exception.login;

public class WrongAuthCodeException extends RuntimeException{
    private static final String msg = "wrongAuthCode";
    public WrongAuthCodeException() {
        super(msg);
    }
}
