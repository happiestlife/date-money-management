package project1.dateMoneyManagement.repository.member;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import project1.dateMoneyManagement.Member;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class MemoryMemberRepositoryTest {

    private MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    private Member member1 = new Member("chickenman10", "1234", "chickenman10@naver.com",
            "chickenman",  "이", "최");
    private Member member2 = new Member("ruri", "0820", "aaaa@naver.com",
            "ruri", "이2", "최2");

    @BeforeEach
    void insertSample() {
        memberRepository.insert(member1);
        memberRepository.insert(member2);
    }

    @Test
    void insertDataWithNoImage() {
        Member member = new Member("test", "1234", "test@naver.com",
                "test",  "이", "최");

        memberRepository.insert(member);

        Member findMember = memberRepository.findById(member.getId());

        assertThat(memberRepository.getSize()).isEqualTo(3);
        assertThat(findMember).isEqualTo(member);
    }

    // 이미지와 함께 회원정보 저장은 아직 만들지 않았다.

    @Test
    void updateMember_Success() {
        String id = member1.getId();
        Member updateMember = new Member(id, "2222", "test@gmail.com",
                                "test", "이test", "최test");

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
    void updateMember_FailByWrongId() {
        String id = "xxxx";
        Member updateMember = new Member(id, "2222", "test@gmail.com",
                "test", "이test", "최test");

        assertThat(memberRepository.update(id, updateMember)).isEqualTo(null);
    }

    @Test
    void removeMember_Success() {
        String id = member1.getId();

        memberRepository.remove(id);
        assertThat(memberRepository.getSize()).isEqualTo(1);

        assertThrows(EmptyResultDataAccessException.class, ()->memberRepository.findById(id));
    }

    @Test
    void removeMember_FailByWrongId() {
        String id = "xxxxx";

        assertThat(memberRepository.remove(id)).isFalse();
    }

    @Test
    void findById_Success() {
        Member member = new Member("test", "2222", "test@gmail.com",
                "test", "이test", "최test");

        memberRepository.insert(member);

        Member findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void findById_FailByWrongId() {
        assertThrows(EmptyResultDataAccessException.class, ()->memberRepository.findById("xxxxx"));
    }

    @Test
    void findAllMembers_Success() {
        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(2);

        String id = member1.getId();
        Member findMember = memberRepository.findById(id);
        assertThat(members).contains(findMember);
    }
}