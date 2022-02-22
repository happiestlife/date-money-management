package project1.dateMoneyManagement.service.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import project1.dateMoneyManagement.Member;
import project1.dateMoneyManagement.exception.login.DuplicateIdException;
import project1.dateMoneyManagement.exception.login.NoEnoughInfoException;
import project1.dateMoneyManagement.exception.login.WrongMatchException;
import project1.dateMoneyManagement.exception.login.WrongIdOrPasswordException;
import project1.dateMoneyManagement.repository.member.FindLoginInfoParam;
import project1.dateMoneyManagement.repository.member.MemberRepository;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final MemberRepository memberRepository;
    private final AuthMailService authMailService;

    private Map<String, AuthCode> idAndCode = new HashMap<>();

    @Scheduled(cron = "* * 3 * * *")
    public void removeTrashCode() {
        Set<String> ids = idAndCode.keySet();

        for (String id : ids) {
            AuthCode code = idAndCode.get(id);
            if(new Date().after(code.getEndDate()))
                ids.remove(id);
        }
    }

    // 로그인
    @Override
    public Member login(String id, String password) {
        Member findMember = memberRepository.findById(id);
        if (findMember != null && findMember.getPassword().equals(password)) {
            return findMember;
        }
        else {
            log.info("Exception occurred - login");

            throw new WrongIdOrPasswordException();
        }
    }

    // 로그아웃은 간단하게 LoginController에서 해결

    // 회원가입
    @Override
    public void register(Member member){
        if(member.getId() == "" ||
            member.getPassword() == "" ||
            member.getEmail() == "" ||
            member.getNickname() == "" ||
            member.getBoyName() == "" ||
            member.getGirlName() == "" ) {
            log.info("Exception occurred - register");

            throw new NoEnoughInfoException();
        }
        else if(memberRepository.insert(member) == false) {
            throw new DuplicateIdException("해당 아이디는 이미 존재합니다.");
        }
    }

    @Override
    public Member findMemberById(String id) {
        Member findMember = memberRepository.findById(id);

        if(findMember == null) {
            log.info("Exception occurred - findMemberById");

            throw new NoEnoughInfoException();
        }
        else return findMember;
    }

    @Override
    public String findIdByEmail(String email) throws NoSuchElementException {
        FindLoginInfoParam findInfo = memberRepository.findByEmail(email);

        if(findInfo == null) {
            log.info("Exception occurred - findIdByEmail");

            throw new NoSuchElementException("해당 이메일에 대한 아이디는 존재하지 않습니다.");
        }
        else
            return findInfo.getId();
    }

    @Override
    public String findPwById(String id, String email) {
        Member findMember = findMemberById(id);
        if(findMember == null || findMember.getEmail().equals(email) == false) {
            log.info("Exception occurred - findPwById");

            throw new NoSuchElementException("아이디가 존재하지 않거나 아이디에 대한 이메일 정보가 일치하지 않습니다.");
        }
        else {
            String code = authMailService.sendMail(new AuthMailDTO(findMember.getEmail(),
                    authMailService.TITLE,
                    authMailService.MESSAGE,
                    null), id);
            idAndCode.put(id, new AuthCode(code));

            return code;
        }
    }

    @Override
    public boolean verifyCode(String id, String code) {
        String getCode = idAndCode.get(id).getCode();
        if (getCode != null && code.equals(getCode))
            return true;
        else {
            log.info("Exception occurred - verifyCode");

            throw new WrongMatchException("코드가 같지 않습니다. 다시 입력해주세요.");
        }
    }

    @Override
    public void updatePw(String id, String newPw, String check) {
        if(newPw.equals(check) == false) {
            log.info("Exception occurred - updatePw");

            throw new WrongMatchException("비밀번호와 비밀번호 확인란이 같지 않습니다. 다시 입력해주세요.");
        }
        Member findMember = memberRepository.findById(id);
        findMember.setPassword(newPw);

        idAndCode.remove(id);

        memberRepository.update(id, findMember);
    }
}
