package project1.dateMoneyManagement.service;

import org.springframework.stereotype.Service;
import project1.dateMoneyManagement.Member;
import project1.dateMoneyManagement.exception.login.DuplicateIdException;
import project1.dateMoneyManagement.exception.login.WrongIdOrPassword;
import project1.dateMoneyManagement.repository.member.MemberRepository;

import java.util.NoSuchElementException;

@Service
public class LoginServiceImpl implements LoginService{

    private MemberRepository memberRepository;

    public LoginServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 로그인
    @Override
    public Member login(String id, String password) {
        Member findMember = memberRepository.findById(id);
        if (findMember != null && findMember.getPassword().equals(password)) {
            return findMember;
        }
        else
            throw new WrongIdOrPassword();
    }

    // 로그아웃은 간단하게 LoginController에서 해결
    @Override
    public void logout(String id) {}

    // 회원가입
    @Override
    public void register(Member member) throws DuplicateIdException{
        memberRepository.insert(member);
    }

    @Override
    public String findIdWithEmail(String email) throws NoSuchElementException {
        return memberRepository.findByEmail(email).getId();
    }
}
