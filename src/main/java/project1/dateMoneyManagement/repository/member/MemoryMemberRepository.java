package project1.dateMoneyManagement.repository.member;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import project1.dateMoneyManagement.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Repository
public class MemoryMemberRepository implements MemberRepository {

    private Map<String, Member> memoryMemberRepository = new ConcurrentHashMap<>();
    private Map<String, FindLoginInfoDTO> emailAndId = new ConcurrentHashMap<>();

    @Override
    public boolean insert(Member member) {
        String memberId = member.getId();
        if (!memoryMemberRepository.containsKey(memberId)) {
            memoryMemberRepository.put(memberId, member);
            emailAndId.put(member.getEmail(), new FindLoginInfoDTO(memberId, member.getPassword()));

            return true;
        }else
            throw new DuplicateKeyException("해당 아이디는 이미 존재합니다.");
    }

    @Override
    public Member update(String memberId, Member updateMember) {
        if (memoryMemberRepository.containsKey(memberId)) {
            Member member = memoryMemberRepository.get(memberId);
            String email = member.getEmail();

            member.setPassword(updateMember.getPassword());
            member.setEmail(updateMember.getEmail());
            member.setNickname(updateMember.getNickname());
            if(updateMember.getImage() != null)
                member.setImage(updateMember.getImage());
            member.setBoyName(updateMember.getBoyName());
            member.setGirlName(updateMember.getGirlName());

            memoryMemberRepository.put(memberId, member);

            FindLoginInfoDTO loginInfo = emailAndId.get(email);
            emailAndId.remove(email);
            emailAndId.put(member.getEmail(), loginInfo);

            return member;
        }
        else
            return null;
    }

    @Override
    public boolean remove(String memberId) {
        if (memoryMemberRepository.containsKey(memberId)) {
            Member member = memoryMemberRepository.get(memberId);
            memoryMemberRepository.remove(memberId);
            emailAndId.remove(member.getEmail());

            return true;
        }
        else
            return false;
    }

    @Override
    public int removeAll() {
        int size = memoryMemberRepository.size();

        memoryMemberRepository.clear();

        return size;
    }

    @Override
    public Member findById(String memberId) {
        Member member = memoryMemberRepository.get(memberId);

        if(member == null)
            throw new EmptyResultDataAccessException(1);
        else
            return member;
    }

    @Override
    public FindLoginInfoDTO findByEmail(String email) {
        FindLoginInfoDTO idInfo = emailAndId.get(email);

        if(idInfo == null)
            throw new EmptyResultDataAccessException(1);
        else
            return idInfo;
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<Member>(memoryMemberRepository.values());
    }

    // 테스트를 위한 메서드
    public int getSize() {
        return memoryMemberRepository.size();
    }

    public void clear() {
        memoryMemberRepository.clear();
    }
}
