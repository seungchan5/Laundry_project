package aug.laundry.service;

import aug.laundry.dao.member.MemberDao;
import aug.laundry.dao.member.MemberMapper;
import aug.laundry.dao.point.PointDao;
import aug.laundry.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl_kgw implements MemberService_kgw{

    private final MemberDao memberDao;

    private final ApiExamMemberProfile apiExam;

    private final BCryptService_kgw bc;

    private final PointDao pointDao;

    public MemberDto selectOne(Long memberId){
        MemberDto memberDto = memberDao.selectOne(memberId);
        return memberDto;
    }

    @Override
    public MemberDto selectId(String memberAccount) {
        return memberDao.selectId(memberAccount);
    }

    public int checkId(String memberAccount){
        int res = memberDao.checkId(memberAccount);

        return res;
    }

    public Integer registerUser(MemberDto memberDto){
        // 비밀번호 암호화
        String password = bc.encodeBCrypt(memberDto.getMemberPassword());
        memberDto.setMemberPassword(password);

        // 핸드폰 번호 형식 수정(예 : 010-1234-5678 -> 01012345678)
        String memberPhone = memberDto.getMemberPhone().replace("-","");
        memberDto.setMemberPhone(memberPhone);

        // 추천인 코드 생성후 DB에 저장
        String inviteCode = getRandomCode();
        memberDto.setMemberMyInviteCode(inviteCode);
        Integer registerRes = memberDao.registerUser(memberDto);

        // 추천인 코드 작성 시 포인트 적립
        MemberDto newbie = memberDao.selectId(memberDto.getMemberAccount());
        if(registerRes > 0){
            int res = pointDao.registerPoint(newbie.getMemberId());
            System.out.println("포인트 넣기 : " + res);
        }
        
        // 추천인 코드를 작성한 경우 신규회원, 추천한 회원에게 포인트 적립
        if(memberDto.getMemberInviteCode() != null && !memberDto.getMemberInviteCode().isBlank()){
            System.out.println("추천인 코드 : " + memberDto.getMemberInviteCode());
            try {
                // 추천인 포인트 적립
                Long recommanderId = memberDao.findRecommender(memberDto.getMemberInviteCode());
                pointDao.addRecommandPoint(recommanderId, 5000, "신규회원에게 추천");
                // 뉴비 포인트 적립
                pointDao.addRecommandPoint(newbie.getMemberId(), 5000, "추천인 코드 작성");
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return registerRes;
    }

    public int inviteCodeCheck(String inviteCode){
        int res = memberDao.inviteCodeCheck(inviteCode);
        return res;
    }

    // 신규유저의 초대코드 생성
    public String getRandomCode(){
        int length = 8; // 생성할 문자열의 길이
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String inviteCode = "";

        Random random = new Random();

        while(true){
            StringBuilder randomCode = new StringBuilder();
            for (int i = 0; i < length; i++) {

                int randomIndex = random.nextInt(characters.length());
                char randomChar = characters.charAt(randomIndex);
                randomCode.append(randomChar);

            }
            inviteCode = randomCode.toString();

            if (inviteCodeCheck(inviteCode) <= 0) {
                System.out.println("초대코드 생성 : " + inviteCode);
                break; // 중복이 아닌 경우 반복문 종료
            }
        }
        return inviteCode;
    }

    public List<MemberDto> confirmId(String memberName, String memberPhone, String memberAccount){
        // 전화번호 형식변경 후 아이디 list에 담기
        String phonenumber = memberPhone.replace("-","");
        
        List<MemberDto> list = memberDao.confirmId(memberName, phonenumber, memberAccount);
        return list;
    }


    public int updatePassword(MemberDto memberDto){
        // 비밀번호 암호화
        String password = bc.encodeBCrypt(memberDto.getMemberPassword());
        memberDto.setMemberPassword(password);

        int res = memberDao.updatePassword(memberDto);

        return res;
    }

    @Override
    public int giveCoupon(Long memberId, Long couponId) {
        return memberDao.giveCoupon(memberId, couponId);
    }

    public int updateAddress(Long memberId, Integer memberZipcode, String memberAddress, String memberAddressDetails){
        return memberDao.updateAddress(memberId, memberZipcode,memberAddress, memberAddressDetails);
    }

    public int getPhoneCnt(String memberPhone){
        return memberDao.getPhoneCnt(memberPhone);
    }

}
