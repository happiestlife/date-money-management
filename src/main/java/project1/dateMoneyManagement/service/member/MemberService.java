package project1.dateMoneyManagement.service.member;

import project1.dateMoneyManagement.model.Member;

public interface MemberService {
    public boolean saveMember(Member member);
    public Member findMemberById(String id);
    public Member updateMember(String id, Member member);
}
