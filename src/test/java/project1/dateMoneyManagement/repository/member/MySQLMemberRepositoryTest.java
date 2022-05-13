package project1.dateMoneyManagement.repository.member;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import project1.dateMoneyManagement.model.Member;
import project1.dateMoneyManagement.repository.CommonRepositoryTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MySQLMemberRepositoryTest {

    private static MySQLlMemberRepository repository;
    private Member member1 = new Member("test1", "test1", "test1@naver.com",
            "test1",  "test1", "test1");
    private Member member2 = new Member("test2", "test2", "test2@naver.com",
            "test2", "test2", "test2");

    @BeforeAll
    static public void init() {
        DataSource ds = CommonRepositoryTest.getDatasource();

        repository = new MySQLlMemberRepository(new JdbcTemplate(ds));
    }

//    @AfterAll
    static public void deleteAll() {
        repository.removeAll();
    }

    @BeforeEach
    void insertSample() {
        repository.removeAll();
        repository.insert(member1);
        repository.insert(member2);
    }

    private void compareMember(Member member1, Member member2) {
        assertThat(member1.getId()).isEqualTo(member2.getId());
        assertThat(member1.getPassword()).isEqualTo(member2.getPassword());
        assertThat(member1.getNickname()).isEqualTo(member2.getNickname());
        assertThat(member1.getEmail()).isEqualTo(member2.getEmail());
        assertThat(member1.getBoyName()).isEqualTo(member2.getBoyName());
        assertThat(member1.getGirlName()).isEqualTo(member2.getGirlName());
        assertThat(member1.getImage()).isEqualTo(member2.getImage());
    }

    @Test
    void insertDataWithNoImage() {
        Member member = new Member("test3", "test3", "test3@naver.com",
                "test3",  "test3", "test3");

        repository.insert(member);

        Member findMember = repository.findById(member.getId());

        assertThat(repository.findAll().size()).isEqualTo(3);

        compareMember(findMember, member);
    }

    // 이미지와 함께 회원정보 저장은 아직 만들지 않았다.

    @Test
    void updateMember_Success() {
        String id = member1.getId();
        Member updateMember = new Member(id, "2222", "test@gmail.com",
                "test", "이test", "최test");

        repository.update(id, updateMember);

        Member findMember = repository.findById(id);

        compareMember(findMember, updateMember);
    }

    @Test
    void updateMember_FailByWrongId() {
        String id = "xxxx";
        Member updateMember = new Member(id, "2222", "test@gmail.com",
                "test", "이test", "최test");

        assertThat(repository.update(id, updateMember)).isEqualTo(null);
    }

    @Test
    void removeMember_Success() {
        String id = member1.getId();

        repository.remove(id);
        assertThat(repository.findAll().size()).isEqualTo(1);

        Member findMember = repository.findById(id);
        assertThat(findMember).isNull();
    }

    @Test
    void removeMember_FailByWrongId() {
        String id = "xxxxx";

        assertThat(repository.remove(id)).isFalse();
    }

    @Test
    void findById_Success() {
        Member findMember = repository.findById(member1.getId());

        compareMember(member1, findMember);
    }

    @Test
    void findById_FailByWrongId() {
        Member findMember = repository.findById("xxxxx");
        assertThat(findMember).isNull();
    }

    @Test
    void findAllMembers_Success() {
        List<Member> members = repository.findAll();
        assertThat(members.size()).isEqualTo(2);

        String id = member1.getId();
        Member findMember = repository.findById(id);
        compareMember(findMember, member1);
    }
}