package project1.dateMoneyManagement.repository.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project1.dateMoneyManagement.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class MemoryMemberRepositoryTest {

    private MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    private List<String> idList = new ArrayList<>();


    @Test
    /**
     * 이미지 없이 회원정보 저장
     */
    void insertDataWithNoImage() {
        Member member = new Member("test", "1234", "test@naver.com",
                "test", null, "현성", "보민");

        memberRepository.insert(member);

        Member findMember = memberRepository.findById(member.getId());

        assertThat(memberRepository.getSize()).isEqualTo(3);
        assertThat(findMember).isEqualTo(member);
    }

    // 이미지와 함께 회원정보 저장은 아직 만들지 않았다.

    @Test
    // 업데이트 성공
    void updateMember_Success() {
        String id = idList.get(0);
        Member updateMember = new Member(id, "2222", "test@gmail.com",
                                "test", "현성test", "보민test");

        memberRepository.update(id, updateMember);

        Member findMember = memberRepository.findById(id);

        assertThat(findMember.getId()).isEqualTo(updateMember.getId());
        assertThat(findMember.getPassword()).isEqualTo(updateMember.getPassword());
        assertThat(findMember.getNickname()).isEqualTo(updateMember.getNickname());
        assertThat(findMember.getEmail()).isEqualTo(updateMember.getEmail());
        assertThat(findMember.getBoyName()).isEqualTo(updateMember.getBoyName());
        assertThat(findMember.getGirlName()).isEqualTo(updateMember.getGirlName());
        assertThat(findMember.getImage()).isEqualTo(updateMember.getImage());
    }

    @Test
    // 업데이트 실패( id가 존재 X )
    void updateMember_Fail() {
        String id = "xxxx";
        Member updateMember = new Member(id, "2222", "test@gmail.com",
                "test", "현성test", "보민test");

        assertThrows(NoSuchElementException.class,
                ()-> memberRepository.update(id, updateMember));
    }

    @Test
    void removeMember_Success() {
        String id = idList.get(0);

        memberRepository.remove(id);
        assertThat(memberRepository.getSize()).isEqualTo(1);

        Member findMember = memberRepository.findById(id);
        assertThat(findMember).isEqualTo(null);
    }

    @Test
    void removeMember_Fail() {
        String id = "xxxxx";

        assertThrows(NoSuchElementException.class,
                ()->memberRepository.remove(id));
    }

    @Test
    void findById_Success() {
        Member member = new Member("test", "2222", "test@gmail.com",
                "test", "현성test", "보민test");

        memberRepository.insert(member);

        Member findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void findById_Fail() {
        Member findMember = memberRepository.findById("xxxxx");
        assertThat(findMember).isEqualTo(null);
    }

    @Test
    void findAllMembers() {
        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(2);

        String id = idList.get(0);
        Member findMember = memberRepository.findById(id);
        assertThat(members).contains(findMember);
    }


    @BeforeEach // 각 테스트 메서드 실행 전에 실행됨
    void insertSample() {
        Member member1 = new Member("chickenman10", "1234", "chickenman10@naver.com",
                "chickenman", null, "현성", "보민");
        Member member2 = new Member("ruri", "0820", "aaaa@naver.com",
                "ruri", null, "현성2", "보민2");

        memberRepository.insert(member1);
        memberRepository.insert(member2);

        idList.add(member1.getId());
        idList.add(member2.getId());
    }
}