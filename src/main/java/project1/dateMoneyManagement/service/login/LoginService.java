package project1.dateMoneyManagement.service.login;

import project1.dateMoneyManagement.model.Member;

public interface LoginService {
    public Member login(String id, String password);
    public boolean register(Member member);
    public Member findMemberById(String id);
    public String findIdByEmail(String email);
    public String sendAuthCode(String id, String email);
    public boolean verifyCode(String id, String code);
    public boolean updatePw(String id, String pw, String check);
}
