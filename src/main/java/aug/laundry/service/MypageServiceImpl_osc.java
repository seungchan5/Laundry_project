package aug.laundry.service;

import aug.laundry.dao.mypage.MypageDao;
import aug.laundry.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MypageServiceImpl_osc implements MypageService_osc {

  private final MypageDao mypageDao;

  @Override
  public String findByName(Long memberId) {
    return mypageDao.findByName(memberId);
  }

  @Override
  public MypageDto findByNameAndPass(Long memberId) {
    return mypageDao.findByNameAndPass(memberId);
  }

  @Override
  public String findByInviteCode(Long memberId) {
    return mypageDao.findByInviteCode(memberId);
  }

  @Override
  public MypageDto findByInfo(Long memberId) {
    return mypageDao.findByInfo(memberId);
  }

  @Override
  public int updateAddress(Long memberId, UpdateAddressDto updateAddressDto) {
    return mypageDao.updateAddress(memberId, updateAddressDto);
  }

  @Override
  public int updatePhone(Long memberId, UpdatePhoneDto updatePhoneDto) {

    return mypageDao.updatePhone(memberId, updatePhoneDto);
  }

  @Override
  public int unregister(Long memberId) {
    return mypageDao.unregister(memberId);
  }

  @Override
  public int changePassword(Long memberId, ChangePasswordDto changePasswordDto) {
    return mypageDao.changePassword(memberId, changePasswordDto);
  }

  @Override
  public List<MyPointDto> getPoint(Long memberId) {
    return mypageDao.getPoint(memberId);
  }

  @Override
  public PointNowDto getPointNow(Long memberId) {
    return mypageDao.getPointNow(memberId);
  }

  @Override
  public int someCoupon(Long memberId) {
    return mypageDao.someCoupon(memberId);
  }

  @Override
  public MySubscribeMonthsDto findMySubscribeMonths(Long memberId) {
    return mypageDao.findMySubscribeMonths(memberId);
  }


}
