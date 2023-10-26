package aug.laundry.dao.member;

import aug.laundry.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MemberDao {
    private final MemberMapper memberMapper;
    public MemberDto selectOne(Long memberId){return memberMapper.selectOne(memberId);}
    public int checkId(String memberAccount){return memberMapper.checkId(memberAccount);}
    public Integer registerUser(MemberDto memberDto){return memberMapper.registerUser(memberDto);}
    public int inviteCodeCheck(String inviteCode){return memberMapper.inviteCodeCheck(inviteCode);};
    public Long findRecommender(String inviteCode){return memberMapper.findRecommender(inviteCode);}
    public MemberDto selectId(String memberAccount){return memberMapper.selectId(memberAccount);}
    public List<MemberDto> confirmId(@Param("memberName") String memberName, @Param("memberPhone") String memberPhone, @Param("memberAccount") String memberAccount){
        return memberMapper.confirmId(memberName, memberPhone, memberAccount);
    };
    public int updatePassword(MemberDto memberDto){return memberMapper.updatePassword(memberDto);}
    public int giveCoupon(@Param("memberId")Long memberId, @Param("couponId")Long couponId){return memberMapper.giveCoupon(memberId,couponId);}
    public int updateAddress(Long memberId, Integer memberZipcode, String memberAddress, String memberAddressDetails){return  memberMapper.updateAddress(memberId, memberZipcode, memberAddress, memberAddressDetails);}

    public int getPhoneCnt(String memberPhone){return memberMapper.getPhoneCnt(memberPhone);}
}
