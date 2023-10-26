package aug.laundry.service;

import aug.laundry.dto.AdminDto;
import aug.laundry.dto.MemberDto;
import aug.laundry.dto.QuickRiderDto;
import aug.laundry.dto.RiderDto;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;

@Service
public interface LoginService_kgw {

    public void naverLogin(HttpServletRequest request, Model model, HttpSession session);

    public void kakaoProcess(String code, HttpSession session);

    public int registerSocialUser(MemberDto memberDto);

    public int registerSocialNumber(String id);

    public MemberDto login(MemberDto memberDto);

    public AdminDto adminLogin(String adminEmail, String adminPassword);

    public RiderDto riderLogin(String riderEmail, String riderPassword);

    public QuickRiderDto quickRiderLogin(String quickRiderEmail, String quickRiderPassword);

    public int keepLogin(String sessionId, Date limit, Long memberId);

    public MemberDto checkUserWithSessionId(String sessionId);

    public int renewLoginTime (Long memberId);


}
