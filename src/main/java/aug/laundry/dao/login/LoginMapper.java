package aug.laundry.dao.login;
import aug.laundry.dto.AdminDto;
import aug.laundry.dto.MemberDto;
import aug.laundry.dto.QuickRiderDto;
import aug.laundry.dto.RiderDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.sql.Date;

@Mapper
public interface LoginMapper {
    public int checkSocialId(String id);

    public int registerSocialUser(MemberDto memberDto);

    public int registerSocialNumber(String id);

    public MemberDto socialLogin(@Param("memberAccount") String memberAccount, @Param("memberSocial") String memberSocial);

    public MemberDto login(String memberAccount);

    public AdminDto adminLogin(@Param("adminEmail") String adminEmail, @Param("adminPassword") String adminPassword);

    public RiderDto riderLogin(@Param("riderEmail")String riderEmail, @Param("riderPassword") String riderPassword);

    public QuickRiderDto quickRiderLogin(@Param("quickRiderEmail")String quickRiderEmail, @Param("quickRiderPassword") String quickRiderPasswords);

    public int keepLogin(@Param("sessionId") String sessionId, @Param("limit") Date limit, @Param("memberId") Long memberId);

    public MemberDto checkUserWithSessionId(String sessionId);

    public int renewLoginTime (Long memberId);



}
