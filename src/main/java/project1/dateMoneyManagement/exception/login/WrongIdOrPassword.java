package project1.dateMoneyManagement.exception.login;

public class WrongIdOrPassword extends RuntimeException{
    private static final String msg = "아이디가 존재하지 않거나 비밀번호가 틀립니다.";
    public WrongIdOrPassword() {
        super(msg);
    }
}
