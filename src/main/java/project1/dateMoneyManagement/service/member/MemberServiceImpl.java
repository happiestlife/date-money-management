package project1.dateMoneyManagement.service.member;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import project1.dateMoneyManagement.model.Member;
import project1.dateMoneyManagement.repository.member.FindLoginInfoDTO;
import project1.dateMoneyManagement.repository.member.MemberRepository;

import java.util.NoSuchElementException;

@Service
public class MemberServiceImpl implements MemberService{

    private MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public boolean saveMember(Member member) {
        FindLoginInfoDTO findId = memberRepository.findByEmail(member.getEmail());
        if(findId == null)
            throw new DuplicateKeyException("duplicateEmail");

        memberRepository.insert(member);

        return true;
    }

    @Override
    public Member findMemberById(String id) {
        Member findMember = memberRepository.findById(id);
        if(findMember == null)
            throw new NoSuchElementException("noIdExist");

        return findMember;
    }

    @Override
    public Member updateMember(String id, Member member) {
        Member updateMember = memberRepository.update(id, member);
        if(updateMember == null)
            throw new DuplicateKeyException("duplicateEmailExist");

        return updateMember;
    }
}
