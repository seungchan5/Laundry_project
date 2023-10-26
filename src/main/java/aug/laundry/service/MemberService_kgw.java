package aug.laundry.service;

import aug.laundry.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


@Service
public interface MemberService_kgw {
    public MemberDto selectOne(Long memberId);

    public MemberDto selectId(String memberAccount);

    public int checkId(String memberAccount);

    public Integer registerUser(MemberDto memberDto);

    public int inviteCodeCheck(String inviteCode);

    public List<MemberDto> confirmId(String memberName, String memberPhone, String memberAccount);

    public int updatePassword(MemberDto memberDto);
    public int giveCoupon(Long memberId,Long couponId);
    public int updateAddress(Long memberId, Integer memberZipcode, String memberAddress, String memberAddressDetails);

    public int getPhoneCnt(String memberPhone);


}
