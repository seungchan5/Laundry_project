package aug.laundry.dao;

import aug.laundry.dao.subscribe.SubscribeDao;
import aug.laundry.dto.SubscriptionPayDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SubscribeTest {

    @Autowired
    private SubscribeDao subscribeDao;

    @Test
    public void insertSubDataTest() {
//        SubscriptionPayDto data = new SubscriptionPayDto();
//        data.setMemberId(1L);
//        data.setSelectMonth(1);
//        data.setMerchantUid("merchant_uid_0001");
//        data.setCustomerUid("customer_uid_0001");
//        data.setName("김승우");
//        data.setAmount(5900);
//
//        int res = subscribeDao.insertJoinSubscribe(data);

//        int res1 = subscribeDao.updateMemberSubscribe(1, 1L);

//        Assertions.assertThat(res1).isEqualTo(1);
    }

}
