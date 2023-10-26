package aug.laundry.service;

import aug.laundry.dao.rider.RiderMapper;
import aug.laundry.domain.DeliveryImage;
import aug.laundry.domain.Orders;
import aug.laundry.domain.Rider;
import aug.laundry.dto.OrdersEnum;
import aug.laundry.enums.orderStatus.OrderStatus;
import aug.laundry.enums.orderStatus.routineOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService{

    private final RiderMapper riderMapper;


//    @Override
//    public List<OrdersEnum> OrderList(String status, Long riderId) {
//        List<Orders> orders = riderMapper.orderList(status, riderId);
//        List<OrdersEnum> change = OrderStatus.change(orders);
//        return change;
//    }
    @Override
    public List<OrdersEnum> OrderListEnum(String status, Long quickRiderId, String workingArea) {
        List<OrdersEnum> ordersEnums = riderMapper.orderListEnum(status, quickRiderId, workingArea);
        ordersEnums.forEach(x -> x.setOrdersStatus(OrderStatus.valueOf("R" + x.getOrdersStatus()).getTitle()));
        return ordersEnums;
    }

    @Override
    public List<Map<String, Integer>> orderListCnt(Long quickRiderId, String workingArea) {
        return riderMapper.orderListCnt(quickRiderId, workingArea);
    }

    @Override
    public Orders orderInfo(Long ordersId) {
        return riderMapper.orderInfo(ordersId);
    }

    @Override
    public int updateOrderRider(Orders orders) {
        return riderMapper.updateOrderRider(orders);
    }

    @Override
    public int updateOrderStatus(Long ordersId) {
        return riderMapper.updateOrderStatus(ordersId);
    }

    @Override
    public Rider riderInfo(Long riderId) {
        return riderMapper.riderInfo(riderId);
    }

    @Override
    public DeliveryImage finishImg(Long ordersId) {
        return riderMapper.finishImg(ordersId);
    }

    @Override
    public List<OrdersEnum> routineOrderList(String ordersAddress, String status, Long riderId) {
        List<Orders> orders = riderMapper.routineOrderList(ordersAddress, status, riderId);
        List<OrdersEnum> change = OrderStatus.change(orders);
        return change;
    }

    @Override
    public Rider routineRider(Long riderId) {
        return riderMapper.routineRider(riderId);
    }

    @Override
    public List<Map<String, Integer>> routineOrderCnt(Long riderId) {
        return riderMapper.routineOrderCnt(riderId);
    }

    @Override
    public Map<String, Integer> routineTotalCnt(String zipCode, Long riderId) {
        return riderMapper.routineTotalCnt(zipCode, riderId);
    }

    @Override
    public List<Integer> dongCnt(String ordersAddress, String status) {
        routineOrder order = routineOrder.valueOf(ordersAddress);
        List<String> dongNames = order.getDongName();

        List<Integer> list = new ArrayList<>();

        for(String a : dongNames){
            String address = ordersAddress + " " + a;
            list.add(riderMapper.dongCnt(address, status));
        }
        System.out.println(list);
        return list;
    }

    @Override
    public int acceptCheck(Long ordersId) {
        return riderMapper.acceptCheck(ordersId);
    }

    @Override
    public int updateStatus(Long riderId) {
        return riderMapper.updateStatus(riderId);
    }

    @Override
    public Integer isRoutineDelivery(Long riderId) {
        return riderMapper.isRoutineDelivery(riderId);
    }

    @Override
    public int updateRoutineOrdersRiderId(Long ordersId) {
        return riderMapper.updateRoutineOrdersRiderId(ordersId);
    }

    @Override
    public int deleteOrderId(Long ordersId) {
        return riderMapper.deleteOrderId(ordersId);
    }


//    @Override
//    public List<Map<String, Integer>> dongCnt(String ordersAddress) {
//        routineOrder order = routineOrder.valueOf(ordersAddress);
//        List<String> dongNames = order.getDongName();
//
//        List<Map<String, Integer>> list = new ArrayList<>();
//
//        for(String a : dongNames){
//            String address = ordersAddress + " " + a;
//            list.add(riderMapper.dongCnt(address));
//        }
//        return list;
//    }
}
