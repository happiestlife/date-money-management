package project1.dateMoneyManagement.service.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import project1.dateMoneyManagement.Member;
import project1.dateMoneyManagement.exception.login.*;
import project1.dateMoneyManagement.repository.member.FindLoginInfoDTO;
import project1.dateMoneyManagement.repository.member.MemberRepository;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final MemberRepository memberRepository;
    private final AuthMailService authMailServiceImpl;

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

        // 아이디가 존재 X or 비밀번호 존재 X
        if(findMember == null || findMember.getPassword().equals(password) == false){
            log.info("Exception occurred - register");

            throw new WrongIdOrPasswordException();
        }
        else
            return findMember;
    }

    // 로그아웃은 간단하게 LoginController에서 해결

    // 회원가입
    @Override
    public boolean register(Member member){
        if (member.getId() == "" ||
                member.getPassword() == "" ||
                member.getEmail() == "" ||
                member.getNickname() == "" ||
                member.getBoyName() == "" ||
                member.getGirlName() == "") {
            log.info("Exception occurred - register");

            throw new NoEnoughInfoException();
        }
        else if(memberRepository.insert(member) == false){
            log.info("Exception occurred - register");

            throw new DuplicateIdException("해당 아이디는 이미 존재합니다.");
        }
        else
            return true;
    }

    @Override
    public Member findMemberById(String id) {
        Member findMember = memberRepository.findById(id);

        if(findMember == null){
            log.info("Exception occurred - findMemberById");

            throw new NoSuchElementException();
        }
        else
            return findMember;
    }

    @Override
    public String findIdByEmail(String email) throws NoSuchElementException {
        FindLoginInfoDTO findInfo = memberRepository.findByEmail(email);

        if (findInfo == null) {
            log.info("Exception occurred - findIdByEmail");

            throw new NoSuchElementException("해당 이메일에 대한 아이디는 존재하지 않습니다.");
        }
        else
            return findInfo.getId();
    }

    // 비밀번호 찾기
    @Override
    public String sendAuthCode(String id, String email) {
        Member findMember = findMemberById(id);
        if(findMember == null || findMember.getEmail().equals(email) == false){
            log.info("Exception occurred - findPwById");

            throw new NoSuchElementException("아이디가 존재하지 않거나 아이디에 대한 이메일 정보가 일치하지 않습니다.");
        } else {
            String code = authMailServiceImpl.sendMail(new AuthMailDTO(findMember.getEmail(),
                    authMailServiceImpl.TITLE,
                    authMailServiceImpl.MESSAGE,
                    null), id);
            idAndCode.put(id, new AuthCode(code));

            return code;
        }
    }

    @Override
    public boolean verifyCode(String id, String code) {
        AuthCode authCode = idAndCode.get(id);

        if(authCode == null)
            throw new NoSuchElementException("해당 아이디는 존재하지 않습니다.");
        else if (code.equals(authCode.getCode()) == false) {
            log.info("Exception occurred - verifyCode");

            throw new WrongAuthCodeException();
        }
        else
            return true;
    }

    @Override
    public boolean updatePw(String id, String newPw, String check) {
        if(newPw.equals(check) == false) {
            log.info("Exception occurred - updatePw");

            throw new WrongMatchException("비밀번호와 비밀번호 확인란이 같지 않습니다. 다시 입력해주세요.");
        }
        Member findMember = memberRepository.findById(id);
        findMember.setPassword(newPw);

        idAndCode.remove(id);

        memberRepository.update(id, findMember);

        return true;
    }
}
