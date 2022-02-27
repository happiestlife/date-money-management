package project1.dateMoneyManagement.exception.login;

public class WrongAuthCodeException extends RuntimeException{
    private static final String msg = "코드가 같지 않습니다. 다시 입력해주세요.";
    public WrongAuthCodeException() {
        super(msg);
    }
}
