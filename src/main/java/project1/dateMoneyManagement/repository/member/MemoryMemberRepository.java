package project1.dateMoneyManagement.repository.member;

import org.springframework.stereotype.Repository;
import project1.dateMoneyManagement.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private Map<String, Member> memoryMemberRepository = new ConcurrentHashMap<>();
    private Map<String, FindLoginInfoParam> emailAndId = new ConcurrentHashMap<>();

    @Override
    public boolean insert(Member member) {
        String memberId = member.getId();
        if (!memoryMemberRepository.containsKey(memberId)) {
            memoryMemberRepository.put(memberId, member);
            emailAndId.put(member.getEmail(), new FindLoginInfoParam(memberId, member.getPassword()));

            return true;
        }else
            return false;
    }

    @Override
    public Member update(String memberId, Member updateMember) {
        if (memoryMemberRepository.containsKey(memberId)) {
            Member member = memoryMemberRepository.get(memberId);

            member.setPassword(updateMember.getPassword());
            member.setEmail(updateMember.getEmail());
            member.setNickname(updateMember.getNickname());
            if(updateMember.getImage() != null)
                member.setImage(updateMember.getImage());
            member.setBoyName(updateMember.getBoyName());
            member.setGirlName(updateMember.getGirlName());

            memoryMemberRepository.put(memberId, member);

            String updateEmail = member.getEmail();
            FindLoginInfoParam loginInfo = emailAndId.get(updateEmail);
            emailAndId.remove(updateEmail);
            emailAndId.put(updateEmail, loginInfo);

            return member;
        }
        else
            return null;
//            throw new NoSuchElementException("아이디가 존재하지 않습니다.");
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
//            throw new NoSuchElementException();
    }

    @Override
    public Member findById(String memberId) {
        return memoryMemberRepository.get(memberId);
    }

    @Override
    public FindLoginInfoParam findByEmail(String email) {
        return emailAndId.get(email);
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
