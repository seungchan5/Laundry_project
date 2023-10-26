package aug.laundry.controller;

import aug.laundry.commom.SessionConstant;
import aug.laundry.dao.member.MemberMapper;
import aug.laundry.dto.*;
import aug.laundry.enums.category.Delivery;
import aug.laundry.enums.category.MemberShip;
import aug.laundry.enums.category.Pass;
import aug.laundry.service.LaundryService;
import aug.laundry.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static aug.laundry.commom.ConstOrderStatus.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersServiceKdh;
    private final LaundryService laundryService;
    private final MemberMapper memberMapper;

    @GetMapping
    public String orders(Model model, @SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false)Long memberId){

        List<CategoryForOrdersListDto> category = ordersServiceKdh.findCategoryByMemberId(memberId);
        List<OrdersForOrdersListDto> orders = ordersServiceKdh.findOrders(memberId);

        log.info("category={}", category);
        log.info("orders={}",orders);
        model.addAttribute("categories", category);
        model.addAttribute("orders", orders);

        return "project_order_list_2";
    }


    @GetMapping("/complete")
    public String complete(Model model, @SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false)Long memberId){

        List<CategoryForOrdersListDto> categoryFinished = ordersServiceKdh.findCategoryFinishedByMemberId(memberId);
        List<OrdersForOrdersListDto> ordersFinished = ordersServiceKdh.findOrdersFinished(memberId);

        log.info("ordersFinsihed={}",ordersFinished);
        model.addAttribute("categoriesFinished", categoryFinished);
        model.addAttribute("ordersFinished", ordersFinished);

        return "project_order_list_complete";
    }

    @GetMapping("/{ordersId}/payment")
    public String payOrder(@PathVariable Long ordersId,
                           @SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false)Long memberId,
                           Model model){
        
        OrdersResponseDto ordersResponseDto = ordersServiceKdh.findByOrdersId(ordersId);

        if(memberId != ordersResponseDto.getMemberId()){
            throw new IllegalStateException("비정상적인 요청입니다. (결제회원과 로그인회원이 일치하지않음)");
        }
        MemberShip memberShip = laundryService.isPass(memberId);
        Pass pass = memberShip.getCheck();

        MemberDto member = memberMapper.selectOne(memberId); // 멤버아이디 바꾸기
        model.addAttribute("member", member);
        Map<String, Object> dryMap = ordersServiceKdh.findDryCleaningByOrdersId(ordersId);
        Map<String, Object> repairMap = ordersServiceKdh.findRepairByOrdersId(ordersId);
        boolean isQuickLaundry = ordersServiceKdh.isQuickLaundry(ordersId);
        Integer point = ordersServiceKdh.findPointByMemberId(memberId);

        log.info("getCommonLaundryPrice={}",ordersResponseDto.getCommonLaundryPrice());
        log.info("totalDryPrice={}",(Long)dryMap.get("totalDryPrice"));
        log.info("totalRepairPrice={}",(Long)repairMap.get("totalRepairPrice"));
        //배송금액 로직 (생활빨래, 드라이클리닝, 수선만 포함)(배송비X 빠른세탁 X)
        Long totalPrice = ordersResponseDto.getCommonLaundryPrice() +
                (Long)dryMap.get("totalDryPrice") +
                (Long)repairMap.get("totalRepairPrice");

        DeliveryResponseDto delivery = setDeliveryPrice(totalPrice, isQuickLaundry);

        Long deliveryPrice = calcDeliveryPrice(isQuickLaundry, totalPrice);

        Long totalPriceWithDeliveryPrice = deliveryPrice + totalPrice;

        boolean isPass = false;
        Long totalPriceWithPassApplied = null;
        if(pass == Pass.PASS){
            isPass = true;
            totalPriceWithPassApplied = memberShip.apply(totalPrice);
            totalPriceWithDeliveryPrice = deliveryPrice + totalPriceWithPassApplied;
        }

        Long subscriptionDiscountPrice = (totalPriceWithPassApplied==null) ? 0L : (totalPrice - totalPriceWithPassApplied);

        model.addAttribute("subscriptionDiscount", subscriptionDiscountPrice);
        log.info("subscriptionDiscount={}", subscriptionDiscountPrice);

        model.addAttribute("totalPriceWithDeliveryPrice", totalPriceWithDeliveryPrice);

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalPriceWithPassApplied", totalPriceWithPassApplied);


        log.info("dryMap={}", dryMap);
        log.info("repairMap={}", repairMap);
        model.addAttribute("isPass", isPass);
        model.addAttribute("order", ordersResponseDto);
        model.addAttribute("dryMap", dryMap);
        model.addAttribute("repairMap", repairMap);
        model.addAttribute("point", point);
        model.addAttribute("memberId", memberId);
        model.addAttribute("quickLaundry",
                new QuickLaundryResponseDto(Delivery.QUICK_DELIVERY, isQuickLaundry));
        model.addAttribute("delivery", delivery);

        Integer ordersStatus = ordersResponseDto.getOrdersStatus();

        if(ordersStatus==PAY_SUCCESS || ordersStatus==WASH_ING ||
                ordersStatus==WASH_SUCCESS || ordersStatus==BEFORE_TAKE_WHEN_WASH_SUCCESS ||
                ordersStatus==TAKE_SUCCESS_AFTER_WASH_SUCCESS || ordersStatus==DELIVERY_SUCCESS){
            PriceResponseDto price = ordersServiceKdh.findPricesByOrdersId(ordersId);
            log.info("price={}",price);
            model.addAttribute("price", price);
        }


        return "project_order_view";
    }


    private static Long calcDeliveryPrice(boolean isQuickLaundry, Long totalPrice) {
        Long deliveryPrice = null;
        if(isQuickLaundry){
            deliveryPrice = Delivery.COMMON_DELIVERY.getPrice() + Delivery.QUICK_DELIVERY.getPrice();
        } else {
            deliveryPrice = (totalPrice >=30000) ? 0L : Delivery.COMMON_DELIVERY.getPrice();
        }
        return deliveryPrice;
    }

    private static DeliveryResponseDto setDeliveryPrice(Long totalPrice, boolean isQuickLaundry) {
        DeliveryResponseDto delivery = new DeliveryResponseDto(Delivery.COMMON_DELIVERY);

        //빠른배송이 아니라면
        if(!isQuickLaundry){
            //총액이 3만원이 넘으면 배송비 없음
            if(totalPrice >= 30000){
                delivery.setDeliveryStatus(false);
            } else {
                delivery.setDeliveryStatus(true);
            }
        } //빠른배송이라면 배송비 있음
        else {
            delivery.setDeliveryStatus(true);
        }

        return delivery;
    }

    @GetMapping("/{ordersId}/members/{memberId}/coupons")
    public String coupons(@PathVariable Long ordersId, @PathVariable Long memberId, Model model, String takeDate){

        Long expectedPrice = ordersServiceKdh.findExpectedPriceByOrdersId(ordersId);
        List<MyCoupon> getCoupon = ordersServiceKdh.getCoupon(memberId);
        log.info("getCoupon={}",getCoupon);

        model.addAttribute("memberId", memberId);
        model.addAttribute("coupon", getCoupon);
        model.addAttribute("expectedPrice", expectedPrice);

        return "project_use_coupon2";
    }







}

