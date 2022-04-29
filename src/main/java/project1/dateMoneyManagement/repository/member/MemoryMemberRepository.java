package project1.dateMoneyManagement.repository.member;

import project1.dateMoneyManagement.DTO.login.FindLoginInfoDTO;
import project1.dateMoneyManagement.model.Member;

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
            return false;
//            throw new DuplicateKeyException("해당 아이디는 이미 존재합니다.");
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
        return memoryMemberRepository.get(memberId);
//        throw new EmptyResultDataAccessException(1);
    }

    @Override
    public FindLoginInfoDTO findByEmail(String email) {
        return emailAndId.get(email);


//            throw new EmptyResultDataAccessException(1);
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
