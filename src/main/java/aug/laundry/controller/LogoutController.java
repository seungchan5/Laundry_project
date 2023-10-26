package aug.laundry.controller;

import aug.laundry.service.LoginService_kgw;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;

@Controller
@RequiredArgsConstructor
public class LogoutController {

    private final LoginService_kgw service;

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        Long memberId = (Long)session.getAttribute("memberId");
        if(session != null){
            session.invalidate();
            Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
            if(loginCookie != null){
                // null이 아니면 존재하면!
                loginCookie.setPath("/");
                // 쿠키는 없앨 때 유효시간을 0으로 설정하는 것 !!! invalidate같은거 없음.
                loginCookie.setMaxAge(0);
                // 쿠키 설정을 적용한다.
                response.addCookie(loginCookie);

                // 사용자 테이블에서도 유효기간을 현재시간으로 다시 세팅해줘야함.
                Date date = new Date(System.currentTimeMillis());
                service.keepLogin(sessionId, date, memberId);
            }

        }
        return "redirect:/login";
    }

    @GetMapping("/riderLogout")
    public String riderLogout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/login";
    }

    @GetMapping("/adminLogout")
    public String adminLogout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/login";
    }
}
