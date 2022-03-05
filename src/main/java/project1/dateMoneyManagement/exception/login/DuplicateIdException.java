package project1.dateMoneyManagement.exception.login;

public class DuplicateIdException extends RuntimeException {
    private static final String msg = "duplicateIdExist";
    public DuplicateIdException() {
        super(msg);
    }
}
