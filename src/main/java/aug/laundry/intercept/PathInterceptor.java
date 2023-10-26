package aug.laundry.intercept;

import aug.laundry.commom.SessionConstant;
import aug.laundry.dto.MemberDto;
import aug.laundry.service.LoginService_kgw;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class PathInterceptor implements HandlerInterceptor {

    private final LoginService_kgw loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String requestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행 {}", requestURI);
        HttpSession session = request.getSession(true);
        if (session == null || session.getAttribute(SessionConstant.LOGIN_MEMBER) == null) {
            Cookie loginCookie = WebUtils.getCookie(request,"loginCookie");
            if(loginCookie != null){
                String sessionId = loginCookie.getValue();
                MemberDto memberDto = loginService.checkUserWithSessionId(sessionId);
                if(memberDto != null){
                    session.setAttribute(SessionConstant.LOGIN_MEMBER, memberDto.getMemberId());
                    return true;
                }
            }
        }
        return true;
    }
}
