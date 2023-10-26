package aug.laundry.dao;

import aug.laundry.dao.login.LoginMapper;
import aug.laundry.dao.member.MemberMapper;
import aug.laundry.dto.MemberDto;
import aug.laundry.service.LoginService_kgw;
import aug.laundry.service.MemberService_kgw;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class TestTest {

    @Autowired
    private TestDao testDao;

    @Autowired
    private MemberService_kgw memberService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private LoginService_kgw loginService;

    @Test
    void test(){
        int result = testDao.find();
        Assertions.assertThat(result).isEqualTo(2);


    }

    @Test
    void testSelectOne(){
        Long memberId = 7L;
        MemberDto memberDto = memberService.selectOne(memberId);
        System.out.println(memberDto);

    }

    @Test
    void checkSocialId(){
        int res = loginMapper.checkSocialId("i5R4qxWWVIPTeMh3K74_NQwoIkGvd4xT7930Uc9qUgE");
        System.out.println(res);
    }

    @Test
    void checkId(){
        int res = memberMapper.checkId("rlarjsdn531@naver.com");
        System.out.println(res);
    }

    @Test
    void loginTest(){
        MemberDto member = new MemberDto();
        member = loginMapper.login("rlarjsdn531@naver.com");
        System.out.println(member);

    }

}