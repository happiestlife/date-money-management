package project1.dateMoneyManagement.service.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project1.dateMoneyManagement.Member;
import project1.dateMoneyManagement.exception.login.DuplicateIdException;
import project1.dateMoneyManagement.exception.login.WrongCodeException;
import project1.dateMoneyManagement.exception.login.WrongIdOrPassword;
import project1.dateMoneyManagement.repository.member.FindLoginInfoParam;
import project1.dateMoneyManagement.repository.member.MemberRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final MemberRepository memberRepository;
    private final AuthMailService authMailService;

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
    public Member findMemberById(String id) {
        return memberRepository.findById(id);
    }

    @Override
    public String findIdWithEmail(String email) throws NoSuchElementException {
        return memberRepository.findByEmail(email).getId();
    }

    @Override
    public String findPwWithIdAndEmail(String id, String email) {
        FindLoginInfoParam findLoginInfo = memberRepository.findByEmail(email);
        if (findLoginInfo.getId().equals(id)) {
            String code = authMailService.sendMail(new AuthMailDTO(email,
                    authMailService.TITLE,
                    authMailService.MESSAGE,
                    null), id);

            return code;
        }else
            throw new NoSuchElementException("아이디가 존재하지 않거나 아이디에 대한 이메일 정보가 일치하지 않습니다.");
    }

    @Override
    public boolean verifyCode(String id, String code) {
        if(authMailService.verifyCode(id, code))
            return true;
        else
            throw new WrongCodeException();
    }


}
