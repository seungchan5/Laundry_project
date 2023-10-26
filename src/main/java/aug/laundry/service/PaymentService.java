package aug.laundry.service;

import aug.laundry.commom.ConstPaymentStatus;
import aug.laundry.dao.orders.OrdersDao;
import aug.laundry.dao.payment.PaymentDao;
import aug.laundry.dao.point.PointDao;
import aug.laundry.domain.Paymentinfo;
import aug.laundry.dto.CouponCheckDto;
import aug.laundry.dto.DeliveryResponseDto;
import aug.laundry.dto.OrdersResponseDto;
import aug.laundry.dto.PaymentCheckRequestDto;
import aug.laundry.enums.category.Delivery;
import aug.laundry.enums.category.MemberShip;
import aug.laundry.enums.category.Pass;
import aug.laundry.exception.IsNotValidException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PointDao pointDao;
    private final PaymentDao paymentDao;
    private final OrdersDao ordersDao;
    private final OrdersService ordersServiceKdh;
    private final LaundryService laundryService;


    public Map<String, Long> makePrices(Long ordersId, Long memberId){

        OrdersResponseDto ordersResponseDto = ordersServiceKdh.findByOrdersId(ordersId);
        Map<String, Object> repairMap = ordersServiceKdh.findRepairByOrdersId(ordersId);
        Map<String, Object> dryMap = ordersServiceKdh.findDryCleaningByOrdersId(ordersId);

        Map<String, Long> prices = new HashMap<>();

        Long totalPrice = ordersResponseDto.getCommonLaundryPrice() +
                (Long)dryMap.get("totalDryPrice") +
                (Long)repairMap.get("totalRepairPrice");

        prices.put("totalPrice", totalPrice);

        boolean isQuickLaundry = ordersServiceKdh.isQuickLaundry(ordersId);

        DeliveryResponseDto delivery = setDeliveryPrice(totalPrice, isQuickLaundry);

        Long deliveryPrice = calcDeliveryPrice(isQuickLaundry, totalPrice);

        Long totalPriceWithDeliveryPrice = deliveryPrice + totalPrice;

        MemberShip memberShip = laundryService.isPass(memberId);
        Pass pass = memberShip.getCheck();

        Long totalPriceWithPassApplied = null;
        if(pass == Pass.PASS){
            totalPriceWithPassApplied = memberShip.apply(totalPrice);
            totalPriceWithDeliveryPrice = deliveryPrice + totalPriceWithPassApplied;
        }

        prices.put("totalPriceWithDeliveryPrice", totalPriceWithDeliveryPrice);
        prices.put("totalPriceWithPassApplied", totalPriceWithPassApplied);

        log.info("totalPriceWithDeliveryPrice={}",totalPriceWithDeliveryPrice);

        return prices;
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

    public Map<String, Long> isValid(IamportResponse<Payment> irsp, Long paymentinfoId, Long memberId, Long ordersId, PaymentCheckRequestDto payment) {

        Map<String, Long> prices = makePrices(ordersId, memberId);
        log.info("totalPriceWithDeliveryPrice={}", prices.get("totalPriceWithDeliveryPrice"));

        Long pointPrice = payment.getPointPrice();
        
        //사용할 포인트가 내가 가진 포인트보다 더 많은지 검증
        isLessPointThanIHave(paymentinfoId, memberId, payment, pointPrice);

        Long couponListId = payment.getCouponListId();
        Long couponPrice = payment.getCouponPrice();
        
        //유효한 쿠폰인지 검증
        isValidCoupon(paymentinfoId, couponListId, couponPrice);
        
        //금액 계산후 검증해야함
        Long finalValidPrice = prices.get("totalPriceWithDeliveryPrice");

        if (pointPrice == null) {
            pointPrice = 0L;
        }

        if (couponPrice == null) {
            couponPrice = 0L;
        }
        
        //최종가격과 결제금액이 일치하는지 검증
        isEqualsToPaidPrice(irsp, paymentinfoId, pointPrice, couponPrice, finalValidPrice);

        return prices;
    }

    private static void isEqualsToPaidPrice(IamportResponse<Payment> irsp, Long paymentinfoId, Long pointPrice, Long couponPrice, Long finalValidPrice) {
        // 포인트, 쿠폰까지 적용된 최종가격
        Long expectedTotalPrice = finalValidPrice - pointPrice - couponPrice;

        Long amount = irsp.getResponse().getAmount().longValue();

        log.info("서버에서 계산한 최종가격={}",expectedTotalPrice);
        log.info("실제 결제된 가격={}", amount);
        if (!expectedTotalPrice.equals(amount)) {
            throw new IsNotValidException("최종가격과 결제금액이 일치하지 않음[" + paymentinfoId + "]");
        }
    }

    private void isValidCoupon(Long paymentinfoId, Long couponListId, Long couponPrice) {
        if (couponListId != null) {
            CouponCheckDto coupon = paymentDao.findCouponByCouponListId(couponListId);
            if (coupon == null) {
                throw new IsNotValidException("존재하지 않는 쿠폰입니다.[" + paymentinfoId + "]");
            }
            if (!couponPrice.equals(coupon.getCouponPrice())) {
                throw new IsNotValidException("쿠폰 금액이 일치하지 않습니다.[" + paymentinfoId + "]");
            }
            // 1:미사용 2:주문대기 3:완료처리. 정확한 숫자는 ERD확인요망
            if (coupon.getCouponListStatus() == 3) {
                throw new IsNotValidException("이미 사용한 쿠폰입니다.[" + paymentinfoId + "]");
            }
        }
    }

    private void isLessPointThanIHave(Long paymentinfoId, Long memberId, PaymentCheckRequestDto payment, Long pointPrice) {
        if (pointPrice != null) {
            Integer point = pointDao.findByMemberId(memberId);
            log.info("DB point={}",point);
            log.info("클라이언트 point={},", point);
            if (point < payment.getPointPrice()) {
                throw new IsNotValidException(
                        "주문에서 사용한 포인트가 회원이 가지고 있는 포인트보다 많습니다.[" + paymentinfoId + "]");
            }
        }
    }

    @Transactional
    public void savePaymentInfo(Paymentinfo paymentinfo) {
        paymentDao.savePaymentInfo(paymentinfo);
        if(paymentinfo.getPaymentinfoId() == null){
            throw new IllegalStateException("결제정보가 저장되지 않았습니다.");
        }
    }

    public Paymentinfo findPaymentinfoByPaymentinfoId(Long paymentinfoId){
        Paymentinfo paymentinfo = paymentDao.findPaymentinfoByPaymentinfoId(paymentinfoId);
        if(paymentinfo != null){
            if(paymentinfo.getPaymentStatus() == ConstPaymentStatus.REFUND_SUCCESS){
                throw new IllegalStateException("이미 환불된 결제정보입니다.");
            }
        }
        if(paymentinfo == null){
            throw new IllegalArgumentException("결제정보가 존재하지 않습니다.");
        }

        return paymentinfo;
    }

    @Transactional
    public void updateRefundInfoBypaymentinfoId(Long paymentinfoId, String errorMessage){
        int result = paymentDao.updateRefundInfoBypaymentinfoId(paymentinfoId, errorMessage);

        if(result == 0){
            throw new IllegalArgumentException("결제 환불정보를 업데이트 하지 못했습니다.");
        }
    }

    @Transactional
    public void addBonusPoint(Long memberId, Long pointStack){
        int result = pointDao.addBonusPoint(memberId, pointStack);

        if(result == 0){
            throw new IllegalArgumentException("추가 포인트가 저장되지 않았습니다.");
        }
    }

}

