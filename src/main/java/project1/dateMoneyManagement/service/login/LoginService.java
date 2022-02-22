package project1.dateMoneyManagement.service.login;

import project1.dateMoneyManagement.Member;

public interface LoginService {
    public Member login(String id, String password);
    public void register(Member member);
    public Member findMemberById(String id);
    public String findIdByEmail(String email);
    public String findPwById(String id, String email);
    public boolean verifyCode(String id, String code);
    public void updatePw(String id, String pw, String check);
}
