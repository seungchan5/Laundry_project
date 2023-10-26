package aug.laundry.dao.rider;

import aug.laundry.domain.DeliveryImage;
import aug.laundry.domain.Rider;
import aug.laundry.dto.OrdersEnum;
import org.apache.ibatis.annotations.Mapper;
import aug.laundry.domain.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface RiderMapper {

//    List<Orders> orderList(@Param("status")String status, @Param("riderId")Long riderId);
    List<OrdersEnum> orderListEnum(@Param("status")String status, @Param("quickRiderId")Long quickRiderId, @Param("workingArea")String workingArea);

    List<Map<String, Integer>> orderListCnt(@Param("quickRiderId")Long quickRiderId, @Param("workingArea")String workingArea);
    Orders orderInfo(Long ordersId);

    int updateOrderRider(Orders orders);

    int updateOrderStatus(Long orders);

    Rider riderInfo(Long riderId);

    DeliveryImage finishImg(Long ordersId);

    List<Orders> routineOrderList(@Param("ordersAddress")String ordersAddress, @Param("status")String status, @Param("riderId") Long riderId);

    Rider routineRider(Long riderId);

    List<Map<String, Integer>> routineOrderCnt(Long riderId);

    Map<String, Integer> routineTotalCnt(@Param("zipCode")String zipCode, @Param("riderId")Long riderId);

//    Map<String, Integer> dongCnt(String ordersAddress);

    int dongCnt(@Param("ordersAddress")String ordersAddress, @Param("status")String status);

    int acceptCheck(Long ordersId);

    int updateStatus(Long riderId);

    Integer isRoutineDelivery(Long riderId);

    int updateRoutineOrdersRiderId(Long ordersId);

    int deleteOrderId(Long ordersId);
}
