package project1.dateMoneyManagement.repository.member;

import project1.dateMoneyManagement.model.Member;

import java.util.List;

public interface MemberRepository {
    public boolean insert(Member member);
    public Member update(String memberId, Member newMember);
    public boolean remove(String memberId);
    public int removeAll();
    public Member findById(String memberId);
    public List<Member> findAll();
    public FindLoginInfoDTO findByEmail(String email);
}
