package aug.laundry.controller;

import aug.laundry.commom.SessionConstant;
import aug.laundry.dto.*;
import aug.laundry.service.BCryptService_kgw;
import aug.laundry.service.LoginService_kgw;
import aug.laundry.service.MemberService_kgw;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final LoginService_kgw service;
    private final MemberService_kgw memberService;

    @GetMapping("/naver_callback")
    public String naverLogin_callback(HttpServletRequest request, Model model, HttpSession session, String state) {
        service.naverLogin(request, model, session);
        System.out.println("naverLogin =======");
        System.out.println("naver sessionId : " + session.getAttribute("memberId"));
        Long memberId = (Long)session.getAttribute("memberId");
        MemberDto memberDto = memberService.selectOne(memberId);
        
        // 탈퇴한 회원인지 확인
        try {
            char isDelete = memberService.selectOne(memberId).getMemberDeleteStatus();
            if(isDelete == 'Y'){
                model.addAttribute("errorMsg", "이미 탈퇴한 회원입니다.");
                return "project_login";
            }
        }catch (NullPointerException e){
            return "project_login";
        }
        
        // 만약 주소가 저장되어 있지 않으면 주소를 등록하는 페이지로 이동
        if(memberDto.getMemberZipcode() == null){
            return "redirect:/login/social/address";
        }

        if(state != null && !"null".equals(state)){
            return "redirect:" + state;
        }

        return "redirect:/";
    }
    @GetMapping("/kakaoLogin")
    public String kakaoLogin_Redirect(String code, Model model, HttpSession session, String state){
        service.kakaoProcess(code, session);
        Long memberId = (Long)session.getAttribute("memberId");
        MemberDto memberDto = memberService.selectOne(memberId);
        // 탈퇴한 회원인지 확인
        try {
            char isDelete = memberService.selectOne(memberId).getMemberDeleteStatus();
            if(isDelete == 'Y'){
                model.addAttribute("errorMsg", "이미 탈퇴한 회원입니다.");
                return "project_login";
            }
        }catch (NullPointerException e){
            return "project_login";
        }

        if(memberDto.getMemberZipcode() == null){
            return "redirect:/login/social/address";
        }

        // 인터셉터에서 온 redirectURL이 있다면 로그인 후 redirectURL의 경로로 이동
        String redirectURL = state;
        if(redirectURL != null && !redirectURL.isEmpty()){
            model.addAttribute("msg", "주소를 등록해주세요!");
            return "redirect:" + redirectURL;
        }

        return "redirect:/";
    }

    @GetMapping("/social/address")
    public String socialRegisterAddress(MemberDto memberDto, HttpSession session, Model model){
        Long memberId = (Long)session.getAttribute("memberId");

        model.addAttribute("memberId", memberId);
        return "project_social_address";
    }

    @PostMapping("/social/address")
    public String socialUpdateAddress(MemberDto memberDto, Model model, RedirectAttributes redirectAttributes){
        int res = memberService.updateAddress(memberDto.getMemberId(), memberDto.getMemberZipcode(), memberDto.getMemberAddress(), memberDto.getMemberAddressDetails());
        System.out.println("소셜 멤버 주소 업데이트 : " + res);

        // 최근 로그인 기록이 있으면 true 없으면 false
        redirectAttributes.addFlashAttribute("firstLogin", memberDto.getMemberRecentlyDate() == null ? "Y" : "N");

        service.renewLoginTime(memberDto.getMemberId());
        return "redirect:/";
    }




    @PostMapping("/update/address")
    public String updateAddress(){

        return "redirect:/";
    }


    @PostMapping("/loginAction")
    public  String login(MemberDto memberDto, HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        MemberDto userDto = service.login(memberDto);

        AdminDto adminDto = service.adminLogin(memberDto.getMemberAccount(), memberDto.getMemberPassword());
        RiderDto riderDto = service.riderLogin(memberDto.getMemberAccount(), memberDto.getMemberPassword());
        QuickRiderDto quickRiderDto = service.quickRiderLogin(memberDto.getMemberAccount(), memberDto.getMemberPassword());

        String redirectURL = request.getParameter("redirectURL");
        // 로그인 성공
        if (userDto != null) {
            if(userDto.getMemberRecentlyDate() == null){
                // 웰컴쿠폰 지급
                Long welcomeCoupon = 1L;

                memberService.giveCoupon(userDto.getMemberId(), welcomeCoupon);
            }

            // 최근 로그인 기록이 있으면 true 없으면 false
            redirectAttributes.addFlashAttribute("firstLogin", userDto.getMemberRecentlyDate() == null ? "Y" : "N");

            // 최근 로그인 시간 갱신
            service.renewLoginTime(userDto.getMemberId());

            // 세션에 memberId 저장
            session.setAttribute(SessionConstant.LOGIN_MEMBER, userDto.getMemberId());
            Cookie cookie = new Cookie("loginCookie", session.getId());
            cookie.setPath("/");
            int amount = 60 * 60 * 24 * 7;
            cookie.setMaxAge(amount); // 단위는 (초)임으로 7일정도로 유효시간을 설정해 준다.
            // 쿠키를 적용해 준다.
            response.addCookie(cookie);
            Date limit = new Date(System.currentTimeMillis() + (1000*amount));
            // 현재 세션 id와 유효시간을 사용자 테이블에 저장한다.
            service.keepLogin(session.getId(), limit, userDto.getMemberId());

            if(redirectURL != null && !redirectURL.isEmpty()){
                return "redirect:" + redirectURL;
            }
            request.setAttribute("useCookie", request.getParameter("useCookie"));
            return "redirect:/";

        }else if(adminDto != null){
            session.setAttribute(SessionConstant.LOGIN_MEMBER, adminDto.getAdminId());
            return "redirect:/admin/"+session.getAttribute("memberId");
        }else if(riderDto != null){
            session.setAttribute(SessionConstant.LOGIN_MEMBER, riderDto.getRiderId());
            return "redirect:/ride/routine";
        }else if (quickRiderDto != null) {
            session.setAttribute(SessionConstant.LOGIN_MEMBER, quickRiderDto.getQuickRiderId());
            return "redirect:/ride/wait";
        }else{
            try {
                char isDelete = memberService.selectId(memberDto.getMemberAccount()).getMemberDeleteStatus();
                if(isDelete == 'Y'){
                    model.addAttribute("errorMsg", "이미 탈퇴한 회원입니다.");
                }else{
                    model.addAttribute("memberAccount", memberDto.getMemberAccount());
                    model.addAttribute("errorMsg", "아이디 또는 비밀번호를 잘못 입력했습니다.");
                }
            }catch (NullPointerException e){
                model.addAttribute("errorMsg", "아이디 또는 비밀번호를 잘못 입력했습니다.");
                return "project_login";
            }

            return "project_login";
        }

    }

    @PostMapping("/find-account")
    public String confirmId(ConfirmIdDto confirmIdDto, Model model){
        System.out.println("memberAccount : " + confirmIdDto.getMemberAccount());
        List<MemberDto> list = memberService.confirmId(confirmIdDto.getMemberName(), confirmIdDto.getMemberPhone(), confirmIdDto.getMemberAccount());
        model.addAttribute("list", list);
        return "project_search_id_result";
    }


    @ResponseBody
    @PostMapping("/find-pw")
    public Map<String, Object> confirmId(@RequestBody @Valid ConfirmIdDto confirmIdDto){
        List<MemberDto> list = memberService.confirmId(confirmIdDto.getMemberName(), confirmIdDto.getMemberPhone(), confirmIdDto.getMemberAccount());
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        return map;
    }

    @PostMapping("/find-pw/update")
    public String updatePassword(MemberDto memberDto){
        System.out.println("memberDto : " + memberDto);
        int res = memberService.updatePassword(memberDto);
        System.out.println("비밀번호 변경 : " + res);
        return "redirect:/login";
    }


    @GetMapping("/find-pw/update")
    public String updatePasswordForm(@RequestParam("memberAccount") String memberAccount, Model model){
        System.out.println("memberAccount : " + memberAccount);
        model.addAttribute("memberAccount", memberAccount);
        return "project_change_password";
    }

    @GetMapping("/find-account")
    public String goFindAccount(){
        return "project_search_id";
    }

    @GetMapping("/find-pw")
    public String goFindPassword(){return "project_search_password";}

    @GetMapping
    public String goLogin(HttpServletRequest request){
        HttpSession session = request.getSession();
        Cookie loginCookie = WebUtils.getCookie(request,"loginCookie");
        if(loginCookie != null){
            String sessionId = loginCookie.getValue();
            MemberDto memberDto = service.checkUserWithSessionId(sessionId);
            if(memberDto != null){
                session.setAttribute(SessionConstant.LOGIN_MEMBER, memberDto.getMemberId());
                return "redirect:/";
            }

        }
        return "project_login";
    }


}


















