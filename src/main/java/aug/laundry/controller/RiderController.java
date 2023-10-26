package aug.laundry.controller;

import aug.laundry.commom.SessionConstant;
import aug.laundry.domain.DeliveryImage;
import aug.laundry.domain.Orders;
import aug.laundry.domain.Rider;
import aug.laundry.dto.DongInfoDto;
import aug.laundry.dto.OrdersEnum;
import aug.laundry.enums.fileUpload.FileUploadType;
import aug.laundry.enums.orderStatus.routineOrder;
import aug.laundry.service.FileUploadService_ksh;
import aug.laundry.service.RiderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static aug.laundry.enums.orderStatus.routineOrder.regionList;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RiderController {

    private final RiderService riderService;
    private final FileUploadService_ksh fileUpload;

    @GetMapping("/ride/wait")
    public String waitList(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long quickRiderId = (Long)session.getAttribute("memberId");

        Rider riderInfo = riderService.riderInfo(quickRiderId);
//        List<OrdersEnum> orderList = riderService.OrderListEnum("대기중", quickRiderId, riderInfo.getWorkingArea());

        List<Map<String, Integer>> cnt = riderService.orderListCnt(quickRiderId, riderInfo.getWorkingArea());

        System.out.println(quickRiderId);

//        model.addAttribute("orderList", orderList);
        model.addAttribute("cnt", cnt);
        model.addAttribute("riderInfo",riderInfo);

        return "project_rider_list_on_call";
    }

    @GetMapping("/ride/accept")
    public String acceptList(Model model, @SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId) {
//        List<Orders> orderList = riderService.OrderList("진행중");
//        List<OrdersEnum> orderList = riderService.OrderList("진행중", memberId);
        Long quickRiderId = memberId;
        Rider riderInfo = riderService.riderInfo(quickRiderId);
        List<OrdersEnum> orderList = riderService.OrderListEnum("진행중", quickRiderId, riderInfo.getWorkingArea());

        List<Map<String, Integer>> cnt = riderService.orderListCnt(quickRiderId, riderInfo.getWorkingArea());

        model.addAttribute("orderList", orderList);
        model.addAttribute("cnt", cnt);
        model.addAttribute("riderInfo",riderInfo);

        return "project_rider_using_list";
    }

    @GetMapping("/ride/finish")
    public String finishList(Model model, @SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId) {
//        List<Orders> orderList = riderService.OrderList("완료");
        Long quickRiderId = memberId;
        Rider riderInfo = riderService.riderInfo(quickRiderId);
        List<OrdersEnum> orderList = riderService.OrderListEnum("완료", quickRiderId, riderInfo.getWorkingArea());


        List<Map<String, Integer>> cnt = riderService.orderListCnt(quickRiderId, riderInfo.getWorkingArea());

        model.addAttribute("orderList", orderList);
        model.addAttribute("cnt", cnt);
        model.addAttribute("riderInfo",riderInfo);

        return "project_rider_complete";
    }


    @MessageMapping("/order-complete-message/{ordersId}")
//    @SendTo("/topic/order-complete/{ordersId}")
    @SendTo("/topic/order-complete")
    public Map<String, Object> message(@DestinationVariable long ordersId, String message) {
        System.out.println("주문번호 : " + ordersId);
        System.out.println("메세지 도착 :" + message);

        Orders orders = riderService.orderInfo(ordersId);
        System.out.println("==========================info : " + orders);

        Map<String, Object> map = new HashMap<>();

//        Orders orders = new Orders();
//        orders.setOrdersId(37L);
//        orders.setOrdersDate("2023/08/31");
//        orders.setOrdersStatus(2);
////        orders.setOrdersAddress("서울 서대문구 남가좌동 거북골로 84");
//        orders.setOrdersAddress("서울시 서대문구 홍은동 454");
////        orders.setOrdersAddress("서울시 강북구 번동 657");
//        orders.setOrdersAddressDetails("108동 505호");

        System.out.println(orders.getOrdersAddress());
//        System.out.println(riderService.riderInfo(5L).getWorkingArea());
//        System.out.println(orders.getOrdersAddress().split(" ")[1]);
//        if(riderService.riderInfo(5L).getWorkingArea().equals(orders.getOrdersAddress().split(" ")[1])
//        ){
            map.put("a",orders);
            map.put("ordersId", ordersId);
            return map;
//        }else{
//            return null;
//        }
    }

    @GetMapping("/oo/63/{ordersId}")
    public String http33(@PathVariable Long ordersId, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
//        model.addAttribute("ordersId", ordersId);

        System.out.println("------------------------------------------------------------------------------ordersId : " + ordersId);
        redirectAttributes.addFlashAttribute("ordersId", ordersId);
//        model.addAttribute("ordersId", ordersId);
        return "redirect:/gogo";
    }

    @GetMapping("/oo/63")
    public String h(){
        return "oo";
    }

    @GetMapping("/ride/orders/{ordersId}")
    public String orderInfo(@PathVariable("ordersId") Long ordersId, Model model, HttpSession session) {
        Orders info = riderService.orderInfo(ordersId);
        DeliveryImage img = riderService.finishImg(ordersId);

        System.out.println(info);
        System.out.println(img);
        // 이미지가 없을때 img에 담기는거 처리 해줘야함

//        if(session.getAttribute("msg") != null){
//            String msg = (String)session.getAttribute("errMsg");
//            System.out.println("msg : "  + msg);
//            model.addAttribute("msg", msg);
//        }
        model.addAttribute("img", img);
        model.addAttribute("info", info);
        return "project_rider_read_more";
    }

    @PostMapping("/ride/assign/{ordersId}/{ordersStatus}")
    public String orderCheck(@PathVariable("ordersId") Long ordersId,
                             @PathVariable("ordersStatus") Long ordersStatus,
                             @SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId,
                             RedirectAttributes redirectAttributes){
        if(ordersStatus == riderService.acceptCheck(ordersId)){
            Orders orders = new Orders();

            orders.setOrdersId(ordersId);
            orders.setRiderId(memberId);

            int res = riderService.updateOrderRider(orders);
            int res2 = riderService.updateOrderStatus(ordersId);

            System.out.println(res);
            System.out.println(res2);

            return "redirect:/ride/accept";
        }else{
            redirectAttributes.addFlashAttribute("msg", "이미 수락된 배달입니다.");
            return "redirect:/ride/wait";
        }
    }

    @PostMapping("/routine/assign/{ordersId}")
    public String routineCheck(@PathVariable("ordersId") Long ordersId, @SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId, Model model){
        // 정기배달
        Orders orders = new Orders();

        orders.setOrdersId(ordersId);
        orders.setRiderId(memberId);

        int res2 = riderService.updateOrderStatus(ordersId);

        System.out.println(res2);

        return "redirect:/ride/routine";
    }


    @PostMapping("/ride/pickUp/{ordersId}/{riderId}")
    public String deliveryFinish(@PathVariable("ordersId") Long ordersId,
                                 @PathVariable("riderId") Long riderId,
                                 @SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId,
                                 @RequestParam("files") List<MultipartFile> files){
        System.out.println(memberId);
        System.out.println(ordersId);
        System.out.println(files);

        if(riderId != 1){
            // 정기배송
            int fileRes = fileUpload.saveFile(files, ordersId, FileUploadType.DELIVERY);
            System.out.println("fileRes : " + fileRes);
            Orders orders = new Orders();
            orders.setOrdersId(ordersId);
            int res2 = riderService.updateOrderStatus(ordersId);
            return "redirect:/ride/routine/"+ordersId;
        }else{
            // 빠른배송
            int fileRes = fileUpload.saveFile(files, ordersId, FileUploadType.DELIVERY);
            System.out.println("fileRes : " + fileRes);

            Orders orders = new Orders();
            orders.setRiderId(memberId);
            orders.setOrdersId(ordersId);

            int res = riderService.updateOrderRider(orders);
            int res2 = riderService.updateOrderStatus(ordersId);
            return "redirect:/ride/finish";
        }
    }

    @GetMapping("/ride/routine")
    public String routineList(Model model, @SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId){
        Rider rider = riderService.routineRider(memberId);

        routineOrder order = routineOrder.valueOf(rider.getRiderPossibleZipcode());
        List<String> dongNames = order.getDongName();
        System.out.println(dongNames);

        List<OrdersEnum> list = riderService.routineOrderList(dongNames.get(0), "진행중", rider.getRiderId());
        System.out.println(list);

        Map<String,Integer> total = riderService.routineTotalCnt(rider.getRiderPossibleZipcode(), rider.getRiderId());
        System.out.println(total);

        List<Map<String, Integer>> cnt = riderService.routineOrderCnt(rider.getRiderId());
        System.out.println(cnt);

        List<Integer> dongCntWait = riderService.dongCnt(rider.getRiderPossibleZipcode(), "진행중");
        List<Integer> dongCntComplete = riderService.dongCnt(rider.getRiderPossibleZipcode(), "완료");

        List<Map<String, DongInfoDto>> regionCntWait = new ArrayList<>();
        List<Map<String, DongInfoDto>> regionCntComplete = new ArrayList<>();

        for(int i=0; i<dongCntWait.size(); i++){
            DongInfoDto dongInfo = new DongInfoDto();
            dongInfo.setDongNames(dongNames.get(i));
            dongInfo.setDongCnt(dongCntWait.get(i));

            Map<String, DongInfoDto> resultMap = new HashMap<>();
            resultMap.put("dongInfo", dongInfo);
            regionCntWait.add(resultMap);
        }
        for(int i=0; i<dongCntComplete.size(); i++){
            DongInfoDto dongInfo = new DongInfoDto();
            dongInfo.setDongNames(dongNames.get(i));
            dongInfo.setDongCnt(dongCntComplete.get(i));

            Map<String, DongInfoDto> resultMap = new HashMap<>();
            resultMap.put("dongInfo", dongInfo);
            regionCntComplete.add(resultMap);
        }

        model.addAttribute("rider", rider);
        model.addAttribute("list", list);
        model.addAttribute("total", total);
        model.addAttribute("cnt", cnt);
        model.addAttribute("regionCntWait",regionCntWait);
        model.addAttribute("regionCntComplete",regionCntComplete);
//        model.addAttribute("status", "3");
        return "project_rider_routine";
    }


    @GetMapping("/ride/routine/{ordersId}")
    public String routineRead(@PathVariable("ordersId") Long ordersId, Model model){
        Orders info = riderService.orderInfo(ordersId);
        DeliveryImage img = riderService.finishImg(ordersId);

        System.out.println(info);
        System.out.println(img);
        // 이미지가 없을때 img에 담기는거 처리 해줘야함

        model.addAttribute("img", img);
        model.addAttribute("info", info);

        return "project_rider_routine_read_more";
    }

    @GetMapping("/gogo/{ordersId}")
    public String gogo(@PathVariable("ordersId") Long ordersId){
        Integer res = riderService.isRoutineDelivery(ordersId);
        if(res != 0){
            Orders orders = new Orders();
            orders.setOrdersId(ordersId);
            int updateStatus = riderService.updateOrderStatus(ordersId);
            int updateRoutineOrdersRiderId = riderService.updateRoutineOrdersRiderId(ordersId);
            return "project_order_complete";
        }else{
            return "project_order_complete";
        }
    }

    @PostMapping("/ride/update")
    public String update(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId){
        int res = riderService.updateStatus(memberId);
        int res2 = riderService.deleteOrderId(memberId);
        System.out.println(res);
        System.out.println(res2);
        return "redirect:/ride/routine";
    }
}
