package project1.dateMoneyManagement.repository.member;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project1.dateMoneyManagement.model.Member;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Transactional
@Repository
public class MySqlMemberRepository implements MemberRepository {

    private JdbcTemplate jdbcTemplate;
    private final String table = "member";

    private class MemberRowMapper implements RowMapper<Member>{
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            Member member = new Member(rs.getString("id"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("nickname"),
                    rs.getString("boyname"),
                    rs.getString("girlname"),
                    null);

            return member;
        }
    }

    public MySqlMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean insert(Member member) {
        String query = "INSERT INTO %s VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')";
        query = String.format(query,
                table,
                member.getId(),
                member.getPassword(),
                member.getEmail(),
                member.getNickname(),
                member.getBoyName(),
                member.getGirlName(),
                member.getRegDate(),
                member.getImage());

        try {
            jdbcTemplate.update(query);

            return true;
        } catch (DuplicateKeyException e) {
            return false;
        }
    }

    @Override
    public Member update(String memberId, Member newMember) {
        String query = "UPDATE %s SET " +
                "password = '%s', " +
                "email = '%s', " +
                "nickname = '%s',  " +
                "boyname = '%s', " +
                "girlname = '%s', " +
                "regdate = '%s', " +
                "image = '%s' " +
                "WHERE id = '%S'";

        query = String.format(query,
                table,
                newMember.getPassword(),
                newMember.getEmail(),
                newMember.getNickname(),
                newMember.getBoyName(),
                newMember.getGirlName(),
                newMember.getRegDate(),
                newMember.getImage(),
                newMember.getId());

        int flag = jdbcTemplate.update(query);

        return flag == 1? newMember : null;
    }

    @Override
    public boolean remove(String memberId) {
        String query = "DELETE FROM %s WHERE id = '%s'";
        query = String.format(query, table, memberId);

        int flag = jdbcTemplate.update(query);

        return flag == 1? true : false;
    }

    @Override
    public int removeAll() {
        String query = "DELETE FROM " + table;

        int deleteNum = jdbcTemplate.update(query);

        return deleteNum;
    }

    @Override
    public Member findById(String memberId) {
        String query = "SELECT * FROM %s WHERE id = '%s'";

        query = String.format(query, table, memberId);

        try {
            Member member = jdbcTemplate.queryForObject(query, new MemberRowMapper());

            return member;
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Member> findAll() {
        String query = "SELECT * FROM " + table;

        return jdbcTemplate.query(query, new MemberRowMapper());
    }

    @Override
    public FindLoginInfoDTO findByEmail(String email) {
        String query = "SELECT * FROM %s WHERE email = '%s'";

        query = String.format(query, table, email);

        try {
            Member member = jdbcTemplate.queryForObject(query, new MemberRowMapper());

            return new FindLoginInfoDTO(member.getId(), member.getPassword());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @PostConstruct
    public void init() {
        insert(new Member("chickenman10", "1234", "chickenman10@naver.com",
                "chickenman",  "이", "최"));

        insert(new Member("ruri", "0820", "aaaa@naver.com",
                "ruri", "이", "최"));
    }

    @PreDestroy
    public void terminate() {
        jdbcTemplate.update("DELETE FROM "+table);
    }
}
