package project1.dateMoneyManagement.exception.login;

public class NoEnoughInfoException extends RuntimeException{
    private static final String msg = "필요한 정보를 모두 입력해주세요.";
    public NoEnoughInfoException() {
        super(msg);
    }
}
