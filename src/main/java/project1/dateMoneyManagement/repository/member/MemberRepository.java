package project1.dateMoneyManagement.repository.member;

import project1.dateMoneyManagement.Member;

import java.util.List;

public interface MemberRepository {
    public void insert(Member member);
    public Member update(String memberId, Member newMember);
    public void remove(String memberId);
    public Member findById(String memberId);
    public List<Member> findAll();
    public FindLoginInfoParam findByEmail(String email);
}
