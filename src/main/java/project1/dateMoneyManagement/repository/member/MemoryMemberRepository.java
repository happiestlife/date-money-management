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

    @Override
    public void insert(Member member) {
        String memberId = member.getId();
        if (!memoryMemberRepository.containsKey(memberId))
            memoryMemberRepository.put(memberId, member);
    }

    @Override
    public Member update(String memberId, Member newMember) {
        if (memoryMemberRepository.containsKey(memberId)) {
            Member member = memoryMemberRepository.get(memberId);

            member.setPassword(newMember.getPassword());
            member.setEmail(newMember.getEmail());
            member.setNickname(newMember.getNickname());
            if(newMember.getImage() != null)
                member.setImage(newMember.getImage());
            member.setBoyName(newMember.getBoyName());
            member.setGirlName(newMember.getGirlName());

            return member;
        }
        else
            return null;
    }

    @Override
    public Member remove(String memberId) {
        if (!memoryMemberRepository.containsKey(memberId)) {
            Member member = memoryMemberRepository.get(memberId);
            memoryMemberRepository.remove(memberId);

            return member;
        }
        else
            return null;
    }

    @Override
    public Member findById(String memberId) {
        if(memoryMemberRepository.containsKey(memberId))
            return memoryMemberRepository.get(memberId);
        else
            return null;
    }

    @Override
    public List<Member> findAll() {
        if(memoryMemberRepository.size() != 0)
            return new ArrayList<Member>(memoryMemberRepository.values());
        else
            return null;
    }
}
