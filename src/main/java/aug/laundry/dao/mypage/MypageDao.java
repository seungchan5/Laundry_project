package aug.laundry.dao.mypage;

import aug.laundry.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MypageDao {

  private final MypageMapper mypageMapper;

  public String findByName(Long memberId){ return mypageMapper.findByName(memberId); }

  public MypageDto findByNameAndPass(Long memberId){
    return mypageMapper.findByNameAndPass(memberId);
  }

  public String findByInviteCode(Long memberId){ return mypageMapper.findByInviteCode(memberId);}

  public MypageDto findByInfo(Long memberId){ return mypageMapper.findByInfo(memberId); }

  public int updateAddress(Long memberId, UpdateAddressDto updateAddressDto) {
    return mypageMapper.updateAddress(memberId, updateAddressDto.getMemberZipcode(), updateAddressDto.getMemberAddress(), updateAddressDto.getMemberAddressDetails());
  }

  public int updatePhone(Long memberId, UpdatePhoneDto updatePhoneDto){
    return mypageMapper.updatePhone(memberId, updatePhoneDto.getMemberPhone());
  }

  public int unregister(Long memberId) { return mypageMapper.unregister(memberId); }

  public int changePassword(Long memberId, ChangePasswordDto changePasswordDto){
    return mypageMapper.changePassword(memberId, changePasswordDto.getMemberPassword());
  }

  public List<MyPointDto> getPoint(Long memberId){ return mypageMapper.getPoint(memberId); }

  public PointNowDto getPointNow(Long memberId){ return mypageMapper.getPointNow(memberId); }

  public int someCoupon(Long memberId){ return mypageMapper.someCoupon(memberId); }

  public MySubscribeMonthsDto findMySubscribeMonths(Long memberId){return mypageMapper.findMySubscribeMonths(memberId); }
}
