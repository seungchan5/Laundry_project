package aug.laundry.dao.rider;

import aug.laundry.domain.Orders;
import aug.laundry.dto.OrdersEnum;
import aug.laundry.enums.orderStatus.OrderStatus;
import aug.laundry.enums.orderStatus.routineOrder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RiderText {

    private RiderMapper riderMapper;

    @Test
    void test() {
        String title = OrderStatus.valueOf("R" + 1).getTitle();
        Orders o = new Orders();
        o.setOrdersStatus(1);
        List<Orders> list = new ArrayList<>();
        list.add(o);
        List<OrdersEnum> change = OrderStatus.change(list);

        System.out.println("change = " + change);
        System.out.println("title = " + title);
    }

//    @Test
//    void test1(String name){
//        List<String> list = routineOrder.남구.getDongName();
//        routineOrder order = routineOrder.valueOf("남구");
//        List<String> dongNam = order.getDongName();
//        System.out.println(dongNam);
//
//        for(String a : list){
//            System.out.println(a);
//        }
//    }

//    @Test
//    void test11(){
//        List<Map<String, Integer>> list = new ArrayList<>();
//        List<String> li = new ArrayList<>();
//        routineOrder order = routineOrder.valueOf("남구");
//        List<String> dongNames = order.getDongName();
//        System.out.println(dongNames);
//        for(String a : dongNames){
//            String res = "남구" + " " + a;
//            System.out.println(res);
//        }
//        riderMapper.dongCnt("대연동");
//    }
}
