package project1.dateMoneyManagement.service.login;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project1.dateMoneyManagement.Member;
import project1.dateMoneyManagement.exception.login.DuplicateIdException;
import project1.dateMoneyManagement.exception.login.NoEnoughInfoException;
import project1.dateMoneyManagement.exception.login.WrongIdOrPasswordException;
import project1.dateMoneyManagement.exception.login.WrongMatchException;
import project1.dateMoneyManagement.repository.member.MemoryMemberRepository;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {

    private LoginService loginService = new LoginServiceImpl(new MemoryMemberRepository(), new AuthMailServiceTest());
    private Member member = new Member("test", "1234", "test@naver.com",
                                "test", null, "test", "test");

    @BeforeEach
    public void insertData() {
        loginService.register(member);
    }

    @Test
    public void register_Success(){
        Member member = new Member("test1", "1111", "test",
                "test", null, "test", "test");

        loginService.register(member);

        Member findMember = loginService.findMemberById(member.getId());

        assertThat(member).isEqualTo(findMember);
    }

    @Test
    public void register_FailBySameId(){
        Member member1 = new Member("test1", "1", "test1",
                "test1", null, "test1", "test1");
        Member member2 = new Member("test1", "2222", "test2",
                "test2", null, "test2", "test2");

        loginService.register(member1);

        assertThrows(DuplicateIdException.class, () -> loginService.register(member2));
    }

    @Test
    public void register_FailByNotEnoughData(){
        Member noId = new Member("", "1", "test",
                "test", null, "test", "test");
        Member noPw = new Member("test1", "", "test",
                "test", null, "test", "test");
        Member noEmail = new Member("test2", "1", "",
                "test", null, "test", "test");
        Member noNickname = new Member("test3", "1", "test",
                "", null, "test", "test");
        Member noBoyName= new Member("test4", "1", "test",
                "test", null, "", "test");
        Member noGirlName = new Member("test5", "1", "test",
                "test", null, "test", "");

        assertThrows(NoEnoughInfoException.class, () -> loginService.register(noId));
        assertThrows(NoEnoughInfoException.class, () -> loginService.register(noPw));
        assertThrows(NoEnoughInfoException.class, () -> loginService.register(noEmail));
        assertThrows(NoEnoughInfoException.class, () -> loginService.register(noNickname));
        assertThrows(NoEnoughInfoException.class, () -> loginService.register(noBoyName));
        assertThrows(NoEnoughInfoException.class, () -> loginService.register(noNickname));
    }

    @Test
    public void login_Success() {
        Member findMember = loginService.login(member.getId(), member.getPassword());

        Assertions.assertThat(member).isEqualTo(findMember);
    }

    @Test
    public void login_FailByWrongMatch() {
        assertThrows(WrongIdOrPasswordException.class, ()->loginService.login(member.getId(), "wrongPw"));
        assertThrows(WrongIdOrPasswordException.class, ()->loginService.login("wrongId", member.getPassword()));
        assertThrows(WrongIdOrPasswordException.class, ()->loginService.login("wrongId", "wrongPw"));
    }

    @Test
    public void findMemberById_Success() {
        Member findMember = loginService.findMemberById(member.getId());

        Assertions.assertThat(member).isEqualTo(findMember);
    }

    @Test
    public void findMemberById_FailByWrongId() {
        assertThrows(NoSuchElementException.class, () -> loginService.findMemberById("tttt"));
    }

    @Test
    public void findIdByEmail_Success() {
        String findId = loginService.findIdByEmail(member.getEmail());

        assertThat(member.getId()).isEqualTo(findId);
    }

    @Test
    public void findIdByEmail_FailByNoMatchEmail() {
        assertThrows(NoSuchElementException.class, () -> loginService.findIdByEmail("wrongEmail"));
    }

    @Test
    public void sendAuthCode_FailByWrongData() {
        assertThrows(NoSuchElementException.class, () -> loginService.sendAuthCode("wrongId", member.getEmail()));
        assertThrows(NoSuchElementException.class, () -> loginService.sendAuthCode(member.getId(), "wrongEmail"));
        assertThrows(NoSuchElementException.class, () -> loginService.sendAuthCode("wrongId", "wrongEmail"));
    }

    @Test
    public void verifyCode_Success() {
        String code = loginService.sendAuthCode(member.getId(), member.getEmail());

        boolean flag = loginService.verifyCode(member.getId(), code);

        assertThat(flag).isTrue();
    }

    @Test
    public void verifyCode_FailByNoMatchId() {
        String code = loginService.sendAuthCode(member.getId(), member.getEmail());

        assertThrows(WrongMatchException.class, () -> loginService.verifyCode("wrongId", code));
    }

    @Test
    public void verifyCode_FailByWrongMatchCode() {
        String code = loginService.sendAuthCode(member.getId(), member.getEmail());

        assertThrows(WrongMatchException.class, () -> loginService.verifyCode(member.getId(), "wrongCode"));
    }

    @Test
    public void updatePw_Success() {
        boolean flag = loginService.updatePw(member.getId(), "newPassword", "newPassword");

        assertThat(flag).isTrue();
    }

    @Test
    public void updatePw_FailByWrongMatchPasswordAndCheck() {
        assertThrows(WrongMatchException.class, () -> loginService.updatePw(member.getId(), "newPassword", "wrongPassword"));
    }
}
