package project1.dateMoneyManagement.exception.login;

public class WrongCodeException extends RuntimeException{
    private static final String msg = "코드가 틀렸습니다. 다시 입력해주세요";
    public WrongCodeException() {
        super(msg);
    }
}
