package project1.dateMoneyManagement.service.login;

import project1.dateMoneyManagement.Member;

public interface LoginService {
    public Member login(String id, String password);
    public void logout(String id);
    public void register(Member member);
    public String findIdWithEmail(String email);
    public String findPwWithIdAndEmail(String id, String email);
    public boolean verifyCode(String id, String code);
}
