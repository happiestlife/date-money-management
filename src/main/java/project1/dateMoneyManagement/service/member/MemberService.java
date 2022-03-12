package project1.dateMoneyManagement.service.member;

import project1.dateMoneyManagement.controller.member.PasswordCheckDTO;
import project1.dateMoneyManagement.model.Member;

public interface MemberService {
    public boolean saveMember(Member member);
    public Member findMemberById(String id);
    public Member updateMember(String id, Member member);
    public boolean deleteMember(String id, PasswordCheckDTO passwordCheck);
}
