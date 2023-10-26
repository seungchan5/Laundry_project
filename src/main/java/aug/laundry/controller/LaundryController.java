package aug.laundry.controller;

import aug.laundry.commom.SessionConstant;
import aug.laundry.domain.CouponList;
import aug.laundry.dto.*;
import aug.laundry.enums.category.*;
import aug.laundry.enums.repair.RepairCategory;
import aug.laundry.service.LaundryService;
import aug.laundry.service.RiderService;
import aug.laundry.validator.OrderPostValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/laundry")
public class LaundryController {

    private final LaundryService laundryService;
    private final OrderPostValidator orderPostValidator;
    private final RiderService riderService;

    @InitBinder("orderPost")
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(orderPostValidator);
    }

    @GetMapping("/order")
    public String order(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId, Model model) {
        MemberShip memberShip = laundryService.isPass(memberId); // 패스 확인
        Long ordersDetailId = (Long) model.getAttribute("ordersDetailId");
        OrderInfo info = laundryService.orderInfo(model);

        if (checkInfo(info)) return "redirect:/laundry"; // 빠른세탁, 드라이클리닝, 생활빨래, 수선 선택여부가 전부 null이면 /laundry 로 redirect

        Long totalPrice = info.isQuick() ? Delivery.QUICK_DELIVERY.getPrice() : Delivery.COMMON_DELIVERY.getPrice(); // 빠른배송이면 적용되는 가격
        Long discount = 0L;

        List<MyCoupon> coupon = laundryService.getCoupon(memberId); // 내가 보유한 쿠폰
        Address address = laundryService.getAddress(memberId); // 주소 가져오기
        FormatDate dateForm = (FormatDate) model.getAttribute("dateTime"); //  project_order_1 에서 받아온 수거, 배송시간

        if (info.isCommon()) {
            model.addAttribute("common", Category.BASIC);
            totalPrice += memberShip.apply(Category.BASIC.getPrice()); // 생활빨래 기본금액
            discount += Category.BASIC.getPrice() - memberShip.apply(Category.BASIC.getPrice()); // 할인금액
        }
        if (info.isDry()) {
            List<Category> getDry = laundryService.getDry(memberId, ordersDetailId);
            model.addAttribute("dry", getDry);
            Long dryTotalPrice = getDry.stream().map(x -> x.getPrice()).reduce((a, b) -> a + b).get();
            model.addAttribute("dryTotalPrice", dryTotalPrice);
            totalPrice += memberShip.apply(dryTotalPrice); // 드라이클리닝 총 금액
            discount += dryTotalPrice - memberShip.apply(dryTotalPrice); // 할인금액
        }
        if (info.isRepair()) {
            List<RepairCategory> getRepair = laundryService.getRepair(memberId, ordersDetailId);
            System.out.println("getRepair = " + getRepair);
            model.addAttribute("repair", getRepair);
            Long repairTotalPrice = getRepair.stream().map(x -> x.getPrice()).reduce((a, b) -> a + b).get();
            model.addAttribute("repairTotalPrice", repairTotalPrice);
            totalPrice += memberShip.apply(repairTotalPrice); // 수선 총 금액
            discount += repairTotalPrice - memberShip.apply(repairTotalPrice); // 할인금액
        }


        model.addAttribute("delivery", info.isQuick() ? Delivery.QUICK_DELIVERY : Delivery.COMMON_DELIVERY);
        model.addAttribute("memberShip", memberShip.getCheck() == Pass.PASS);
        model.addAttribute("totalPrice", Math.round(totalPrice / 100) * 100);
        model.addAttribute("discount", discount);
        model.addAttribute("quickLaundry", info.isQuick());
        model.addAttribute("info", getJoin(info.isDry(), info.isCommon(), info.isRepair()));
        model.addAttribute("infoPrice", info);
        model.addAttribute("dateTime", dateForm);
        model.addAttribute("address", address);
        model.addAttribute("coupon", coupon);
        model.addAttribute("couponCount", coupon.size());
        return "project_order_confirm";
    }

    private boolean checkInfo(OrderInfo info) {
        // 전부 false 라면 true 반환
        return !info.isQuick() && !info.isDry() && !info.isCommon() && !info.isRepair();
    }

    @PostMapping("/order")
    public String orderPost(@Validated @ModelAttribute OrderPost orderPost, BindingResult bindingResult,
                            @SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId,
                            @SessionAttribute(name = SessionConstant.ORDERS_CONFIRM, required = false) Long ordersDetailId,
                            RedirectAttributes redirectAttributes,
                            HttpSession session) {

        System.out.println("orderPost = " + orderPost);
        if (bindingResult.hasErrors()) {
            System.out.println("bindingResult in Controller = " + bindingResult);
            System.out.println(bindingResult.getAllErrors());
            return "redirect:/laundry/order";
        }
        Long ordersId = laundryService.update(memberId, orderPost.getCoupon(), orderPost, ordersDetailId);// 쿠폰 유효성 검사

        // 주문 완료 후 ordersDetailId를 세션에서 제거
        if (session.getAttribute(SessionConstant.ORDERS_CONFIRM) != null) {
            log.info("SESSION [{}] REMOVE", SessionConstant.ORDERS_CONFIRM);
            session.removeAttribute(SessionConstant.ORDERS_CONFIRM);
        }

        redirectAttributes.addFlashAttribute("ordersId", ordersId);
        return "redirect:/laundry/complete";
    }

    @GetMapping("/complete")
    public String complete(Model model) {
        Long ordersId = (Long) model.getAttribute("ordersId");
        System.out.println("ordersId = " + ordersId);

        Integer res = riderService.isRoutineDelivery(ordersId);
        if(res == 0){ // 일반세탁
            int updateStatus = riderService.updateOrderStatus(ordersId);
            int updateRoutineOrdersRiderId = riderService.updateRoutineOrdersRiderId(ordersId);
            return "project_order_complete";
        }else{ // 빠른세탁
            model.addAttribute("ordersId", ordersId);
            return "project_order_complete";
        }

    }

    @GetMapping("/order/pickup")
    public String pickupLocation() {
        return "project_pickup_location";
    }

    @GetMapping("/order/coupons/select")
    public String selectCoupon(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId, Model model) {
        if (memberId == null) return "redirect:/login";

        List<MyCoupon> getCoupon = laundryService.getCoupon(memberId);
        model.addAttribute("memberId", memberId);
        model.addAttribute("coupon", getCoupon);

        return "project_use_coupon";
    }

    @NotNull
    private static String getJoin(boolean isDry, boolean isCommon, boolean isRepair) {
        int arrLength = 0;
        if (isDry) arrLength++;
        if (isCommon) arrLength++;
        if (isRepair) arrLength++;

        String[] strArr = new String[arrLength];
        int cnt = 0;
        if (isDry) strArr[cnt++] = CategoryOption.DRYCLEANING.getTitle();
        if (isCommon) strArr[cnt++] = CategoryOption.COMMON_LAUNDRY.getTitle();
        if (isRepair) strArr[cnt++] = CategoryOption.REPAIR.getTitle();
        return String.join(",", strArr);
    }




}
