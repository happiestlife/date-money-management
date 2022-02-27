package project1.dateMoneyManagement.repository.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project1.dateMoneyManagement.Member;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {

    private MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    private List<String> idList = new ArrayList<>();

    @BeforeEach
    void insertSample() {
        Member member1 = new Member("chickenman10", "1234", "chickenman10@naver.com",
                "chickenman",  "현성", "보민");
        Member member2 = new Member("ruri", "0820", "aaaa@naver.com",
                "ruri", "현성2", "보민2");

        memberRepository.insert(member1);
        memberRepository.insert(member2);

        idList.add(member1.getId());
        idList.add(member2.getId());
    }

    @Test
    void insertDataWithNoImage() {
        Member member = new Member("test", "1234", "test@naver.com",
                "test",  "현성", "보민");

        memberRepository.insert(member);

        Member findMember = memberRepository.findById(member.getId());

        assertThat(memberRepository.getSize()).isEqualTo(3);
        assertThat(findMember).isEqualTo(member);
    }

    // 이미지와 함께 회원정보 저장은 아직 만들지 않았다.

    @Test
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
    void updateMember_FailByWrongId() {
        String id = "xxxx";
        Member updateMember = new Member(id, "2222", "test@gmail.com",
                "test", "현성test", "보민test");

        assertThat(memberRepository.update(id, updateMember)).isEqualTo(null);
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
    void removeMember_FailByWrongId() {
        String id = "xxxxx";

        assertThat(memberRepository.remove(id)).isFalse();
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
    void findById_FailByWrongId() {
        Member findMember = memberRepository.findById("xxxxx");
        assertThat(findMember).isEqualTo(null);
    }

    @Test
    void findAllMembers_Success() {
        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(2);

        String id = idList.get(0);
        Member findMember = memberRepository.findById(id);
        assertThat(members).contains(findMember);
    }
}