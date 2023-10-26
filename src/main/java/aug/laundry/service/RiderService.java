package aug.laundry.service;

import aug.laundry.domain.DeliveryImage;
import aug.laundry.domain.Orders;
import aug.laundry.domain.Rider;
import aug.laundry.dto.OrdersEnum;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RiderService {

//    List<Orders> orderList(@Param("status")String status, @Param("riderId")Long riderId);
    List<OrdersEnum> OrderListEnum(@Param("status")String status, @Param("quickRiderId")Long quickRiderId, @Param("workingArea")String workingArea);

    List<Map<String, Integer>> orderListCnt(@Param("quickRiderId")Long quickRiderId, @Param("workingArea")String workingArea);

    Orders orderInfo(Long ordersId);

    int updateOrderRider(Orders orders);

    int updateOrderStatus(Long ordersId);

    Rider riderInfo(Long riderId);

    DeliveryImage finishImg(Long ordersId);

    List<OrdersEnum> routineOrderList(@Param("ordersAddress")String ordersAddress, @Param("status")String status, @Param("riderId") Long riderId);

    Rider routineRider(Long riderId);

    List<Map<String, Integer>> routineOrderCnt(Long riderId);

    Map<String, Integer> routineTotalCnt(@Param("zipCode")String zipCode, @Param("riderId")Long riderId);

//    List<Map<String, Integer>> dongCnt(String ordersAddress);

    List<Integer> dongCnt(String ordersAddress, String status);

    int acceptCheck(Long ordersId);

    int updateStatus(Long riderId);

    Integer isRoutineDelivery(Long riderId);

    int updateRoutineOrdersRiderId(Long ordersId);

    int deleteOrderId(Long ordersId);
}
