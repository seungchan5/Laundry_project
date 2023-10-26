package aug.laundry.dao.member;

import aug.laundry.domain.Member;
import aug.laundry.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    public MemberDto selectOne(Long memberId);
    public int checkId(String memberAccount);
    public Integer registerUser(MemberDto memberDto);
    public int inviteCodeCheck(String inviteCode);
    public Long findRecommender(String inviteCode);
    public MemberDto selectId(String memberAccount);
    public List<MemberDto> confirmId(@Param("memberName") String memberName, @Param("memberPhone") String memberPhone, @Param("memberAccount") String memberAccount);
    public int updatePassword(MemberDto memberDto);
    public int giveCoupon(@Param("memberId")Long memberId, @Param("couponId")Long couponId);

    public int updateAddress(@Param("memberId")Long memberId , @Param("memberZipcode")Integer memberZipcode,@Param("memberAddress")String memberAddress, @Param("memberAddressDetails") String memberAddressDetails);

    public int getPhoneCnt(String memberPhone);
}
