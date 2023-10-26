package aug.laundry.mainMapper;

import aug.laundry.dao.MainMapper;
import aug.laundry.dto.OrdersEnum2;
import aug.laundry.enums.category.CategoryPriceCalculator;
import aug.laundry.enums.category.MemberShip;
import aug.laundry.enums.category.Pass;
import aug.laundry.service.MainService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class mainMapperTest {

    @Autowired
    private MainMapper mainMapper;

    @Autowired
    private MainService mainService;

    @Test
    public void test() {
        List<OrdersEnum2> orders = mainMapper.getOrders(1L);
    }

}
