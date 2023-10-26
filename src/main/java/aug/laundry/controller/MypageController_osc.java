package aug.laundry.controller;

import aug.laundry.dto.*;
import aug.laundry.service.BCryptService_kgw;
import aug.laundry.service.LaundryService;
import aug.laundry.service.MemberService_kgw;
import aug.laundry.service.MypageService_osc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MypageController_osc {

  private final MypageService_osc mypageService;
  private final MemberService_kgw memberService;
  private final LaundryService laundryService;
  private final BCryptService_kgw bc;

  @GetMapping("/{memberId}/mypage")
  public String MypageMain(@PathVariable Long memberId, Model model){

    System.out.println("MypageMain");
    MypageDto mypageDto = mypageService.findByNameAndPass(memberId);
    MySubscribeMonthsDto mySubscribeMonthsDto = mypageService.findMySubscribeMonths(memberId);

    // 패스 기간 비교를 위해 날짜 형식 변경
    LocalDate day = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
    String sysdate = day.format(formatter);
    String enddate = mypageDto.getSubscriptionExpireDate();

    String name = mypageDto.getMemberName();
    String pass = mypageDto.getSubscriptionExpireDate();

    String month = "";
    String expireDate = "";

    if(mySubscribeMonthsDto == null){
      month = null;
      expireDate=null;
    } else if(mySubscribeMonthsDto.getSubscriptionId() == 1 && mySubscribeMonthsDto.getSubscriptionExpireDate()!=null){
      month = "1개월권";
      expireDate=mySubscribeMonthsDto.getSubscriptionExpireDate();
    } else if(mySubscribeMonthsDto.getSubscriptionId() == 2 && mySubscribeMonthsDto.getSubscriptionExpireDate()!=null){
      month = "3개월권";
      expireDate=mySubscribeMonthsDto.getSubscriptionExpireDate();
    } else if(mySubscribeMonthsDto.getSubscriptionId() == 3 && mySubscribeMonthsDto.getSubscriptionExpireDate()!=null){
      month = "6개월권";
      expireDate=mySubscribeMonthsDto.getSubscriptionExpireDate();
    } else if(mySubscribeMonthsDto.getSubscriptionId() == 4 && mySubscribeMonthsDto.getSubscriptionExpireDate()!=null){
      month = "12개월";
      expireDate=mySubscribeMonthsDto.getSubscriptionExpireDate();
    }

    // 패스 기간이 null일 경우
    if(pass==null){
      model.addAttribute("userName", name);
      model.addAttribute("userPass", null);
      return "project_mypage_list";
    }
    if(sysdate.compareTo(enddate) > 0){ // 패스 종료기간이 지났을 경우
      model.addAttribute("userName",name);
      model.addAttribute("userPass", null);
    } else if(sysdate.compareTo(enddate)<0){ // 종료기간이 남았을 경우
      model.addAttribute("userName", name);
      pass="PASS";
      model.addAttribute("userPass", pass);
      model.addAttribute("month", month);
      model.addAttribute("expireDate", expireDate);
    } else { // 오늘이 종료일인 경우
      model.addAttribute("userName", name);
      pass="PASS";
      model.addAttribute("userPass", pass);
      model.addAttribute("month", month);
      model.addAttribute("expireDate", expireDate);
    }

    return "project_mypage_list";
  }

  @GetMapping("{memberId}/coupons")
  public String MypageCouponList(@PathVariable Long memberId, Model model){
    List<MyCoupon> getCoupon = laundryService.getCoupon(memberId);

    // 회원 쿠폰 리스트
    int someCoupon = mypageService.someCoupon(memberId);

      model.addAttribute("memberId", memberId);
      model.addAttribute("coupon", getCoupon);
      model.addAttribute("someCoupon", someCoupon);
    return "project_coupon";
  }

  @GetMapping("{memberId}/points")
  public String MypagePointsList(@PathVariable Long memberId, Model model){
    List<MyPointDto> getPoint = mypageService.getPoint(memberId);

    // 회원 포인트 리스트
    if(!getPoint.isEmpty()){
      PointNowDto getpointNow = mypageService.getPointNow(memberId);
      Long pointNow = getpointNow.getPointNow();

      model.addAttribute("memberId", memberId);
      model.addAttribute("point", getPoint);
      model.addAttribute("pointNow", pointNow);

    // 포인트 내역이 없는 경우
    } else {
      model.addAttribute("memberId", memberId);
      model.addAttribute("point", getPoint);
    }


    return "project_point";
  }

  @GetMapping("{memberId}/invite")
  public String MypageInvite(@PathVariable Long memberId, Model model) {

    // 멤버 초대 코드
    String inviteCode = mypageService.findByInviteCode(memberId);

    model.addAttribute("inviteCode", inviteCode);

    return "project_invite";
  }

  @GetMapping("{memberId}/update")
  public String MypageUpdate(@PathVariable Long memberId, Model model){

    // 현재 저장된 개인정보

    MypageDto mypageDto = mypageService.findByInfo(memberId);
    String name = mypageDto.getMemberName();
    String address = "("+mypageDto.getMemberZipcode()+") " + mypageDto.getMemberAddress() + " " + mypageDto.getMemberAddressDetails();
    String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
    String phone = mypageDto.getMemberPhone().replaceAll(regEx, "$1-$2-$3");

    model.addAttribute("userName", name);
    model.addAttribute("userAddress", address);
    model.addAttribute("userPhone", phone);

    return "project_mypage";
  }

  @GetMapping("{memberId}/address/update")
  public String addressUpdate(@PathVariable Long memberId, Model model){
    return "project_update_address";
  }

  @PostMapping("{memberId}/address/update")
  public String addressUpdate(@PathVariable Long memberId, @Valid @ModelAttribute("addressUpdate") UpdateAddressDto addressDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){

    // 회원 주소 변경
    if(bindingResult.hasErrors()){
      redirectAttributes.addFlashAttribute("msg","정확한 주소를 전부 입력해주시기 바랍니다");
      return "redirect:/members/{memberId}/address/update";
    }

    mypageService.updateAddress(memberId, addressDto);
    return "redirect:/members/{memberId}/update";
  }

  @GetMapping("{memberId}/phone/update")
  public String phoneUpdate(@PathVariable Long memberId, Model model){
    return "project_update_phone";
  }

  @PostMapping("{memberId}/phone/update")
  public String phoneUpdate(@PathVariable Long memberId, @Valid @ModelAttribute("phone") UpdatePhoneDto updatePhoneDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
    
    // 회원 핸드폰 번호 변경
    if(bindingResult.hasErrors()){
      redirectAttributes.addFlashAttribute("msg","휴대폰 번호 형식에 맞게 입력하세요");
      return "redirect:/members/{memberId}/phone/update";
    }

    updatePhoneDto.setMemberPhone(updatePhoneDto.getMemberPhone().replace("-",""));
    mypageService.updatePhone(memberId, updatePhoneDto);
    return "redirect:/members/{memberId}/update";
  }

  @GetMapping("{memberId}/unregister")
  public String unregister(@PathVariable Long memberId){
    
    // 회원 탈퇴
    MemberDto memberDto = memberService.selectOne(memberId);
    int res = mypageService.unregister(memberDto.getMemberId());
    return "redirect:/logout";
  }

  @GetMapping("{memberId}/password/update")
  public  String passwordUpdate(@PathVariable Long memberId, Model model){
    return "project_update_password";
  }

  @PostMapping("{memberId}/password/update")
  public String changePassword(@PathVariable Long memberId, @Valid @ModelAttribute("userPw") ChangePasswordDto changePasswordDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){

    // 회원 비밀번호 변경
    if(bindingResult.hasErrors()){
      redirectAttributes.addFlashAttribute("msg","대소문자, 숫자, 특수문자를 포함해서 8자 이상 ~ 15자 이하로 설정해주세요");
      return  "redirect:/members/{memberId}/password/update";
    }

    // 비밀번호 암호화
    changePasswordDto.setMemberPassword(bc.encodeBCrypt(changePasswordDto.getMemberPassword()));
    mypageService.changePassword(memberId, changePasswordDto);
    return "redirect:/members/{memberId}/update";
  }

}
