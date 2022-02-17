package project1.dateMoneyManagement.exception.login;

public class DuplicateIdException extends RuntimeException {
    private static final String msg = "해당 아이디는 이미 존재합니다.";
    public DuplicateIdException(String msg) {
        super(msg);
    }
}
