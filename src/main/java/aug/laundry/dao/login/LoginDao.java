package aug.laundry.dao.login;

import aug.laundry.dto.AdminDto;
import aug.laundry.dto.MemberDto;
import aug.laundry.dto.QuickRiderDto;
import aug.laundry.dto.RiderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
@Slf4j
@RequiredArgsConstructor
public class LoginDao {
    private final LoginMapper loginMapper;

    public int checkSocialId(String id){return loginMapper.checkSocialId(id); }

    public int registerSocialUser(MemberDto memberDto) {return loginMapper.registerSocialUser(memberDto);}

    public int registerSocialNumber(String id){return loginMapper.registerSocialNumber(id);}

    public MemberDto socialLogin(@Param("memberAccount") String memberAccount, @Param("memberSocial") String memberSocial){
        return loginMapper.socialLogin(memberAccount, memberSocial);
    }

    public MemberDto login(String memberAccount){return loginMapper.login(memberAccount);}

    public AdminDto adminLogin(@Param("adminEmail") String adminEmail, @Param("adminPassword") String adminPassword){
        return loginMapper.adminLogin(adminEmail, adminPassword);
    }

    public RiderDto riderLogin(@Param("riderEmail")String riderEmail, @Param("riderPassword") String riderPassword){
        return loginMapper.riderLogin(riderEmail, riderPassword);
    }

    public QuickRiderDto quickRiderLogin(@Param("quickRiderEmail")String quickRiderEmail, @Param("quickRiderPassword") String quickRiderPasswords){
        return loginMapper.quickRiderLogin(quickRiderEmail, quickRiderPasswords);
    }

    public int keepLogin(@Param("sessionId") String sessionId, @Param("limit") Date limit, @Param("memberId") Long memberId){
        return loginMapper.keepLogin(sessionId, limit, memberId);
    }

    public MemberDto checkUserWithSessionId(String sessionId){
        return loginMapper.checkUserWithSessionId(sessionId);
    }

    public int renewLoginTime (Long memberId){return loginMapper.renewLoginTime(memberId);}
}
