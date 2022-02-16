package project1.dateMoneyManagement.repository.member;

import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import project1.dateMoneyManagement.Member;
import project1.dateMoneyManagement.exception.login.DuplicateIdException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private Map<String, Member> memoryMemberRepository = new ConcurrentHashMap<>();

    @Override
    public void insert(Member member) {
        String memberId = member.getId();
        if (!memoryMemberRepository.containsKey(memberId))
            memoryMemberRepository.put(memberId, member);
        else
            throw new DuplicateIdException("해당 아이디는 이미 존재합니다.");
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

            return member;
        }
        else
            throw new NoSuchElementException("아이디가 존재하지 않습니다.");
    }

    @Override
    public void remove(String memberId) {
        if (memoryMemberRepository.containsKey(memberId)) {
            Member member = memoryMemberRepository.get(memberId);
            memoryMemberRepository.remove(memberId);
        }
        else
            throw new NoSuchElementException();
    }

    @Override
    public Member findById(String memberId) {
        return memoryMemberRepository.get(memberId);
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<Member>(memoryMemberRepository.values());
    }

    public int getSize() {
        return memoryMemberRepository.size();
    }

    public void clear() {
        memoryMemberRepository.clear();
    }
}
