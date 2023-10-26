package aug.laundry.service;

import aug.laundry.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MypageService_osc {
  public String findByName(Long memberId);

  public MypageDto findByNameAndPass(Long memberId);
  public String findByInviteCode(Long memberId);
  public MypageDto findByInfo(Long memberId);

  public int updateAddress(Long memberId, UpdateAddressDto updateAddressDto);

  public int updatePhone(Long memberId, UpdatePhoneDto updatePhoneDto);

  public int unregister(Long memberId);

  public int changePassword(Long memberId, ChangePasswordDto changePasswordDto);

  public List<MyPointDto> getPoint(Long memberId);

  public PointNowDto getPointNow(Long memberId);

  public int someCoupon(Long memberId);

  public MySubscribeMonthsDto findMySubscribeMonths(Long memberId);
}
