package aug.laundry.service;

import aug.laundry.dto.OrdersEnum;
import aug.laundry.dto.OrdersEnum2;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

public interface MainService {
    List<OrdersEnum2> getOrders(Long memberId);
    Map<String, Map<String, Long>> getCategory();
}
