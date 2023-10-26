package aug.laundry.dao.orders;

import aug.laundry.commom.ConstCouponListStatus;
import aug.laundry.commom.ConstOrderStatus;
import aug.laundry.domain.Drycleaning;
import aug.laundry.domain.Orders;
import aug.laundry.domain.Repair;
import aug.laundry.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class OrdersDao {

    private final OrdersMapper ordersMapper;

    public OrdersResponseDto findByOrdersId(Long ordersId){
        OrdersResponseDto findOrder = ordersMapper.findByOrdersId(ordersId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        log.info("orderResponseDto={}", findOrder);
        return findOrder;
    }

    public List<Drycleaning> findDryCleaningByOrdersId(Long ordersId){
        List<Drycleaning> drycleaningList = ordersMapper.findDryCleaningByOrdersId(ordersId);
        log.info("drycleaningList={}", drycleaningList);

        if(drycleaningList == null || drycleaningList.isEmpty()){
            return Collections.EMPTY_LIST;
        } else {
            return drycleaningList;
        }
    }

    public List<Repair> findRepairByOrdersId(Long ordersId){
        List<Repair> repairList = ordersMapper.findRepairByOrdersId(ordersId);
        log.info("repairList={}", repairList);

        if(repairList == null || repairList.isEmpty()){
            return Collections.EMPTY_LIST;
        } else {
            return repairList;
        }
    }

    public boolean isQuickLaundry(Long ordersId){
        if(ordersMapper.findQuickLaundryByOrdersId(ordersId)==1){
            return true;
        } else{
            return false;
        }
    }

    public int updateExpectedNDiscountPriceByOrdersId(Long ordersId, Long expectedPrice, Long subscriptionDiscountPrice){
        int i = ordersMapper.updateExpectedNDiscountPriceByOrdersId(ordersId, expectedPrice, subscriptionDiscountPrice);
        log.info("updateExpectedPriceByOrdersId={}", i);
        return i;
    }

    public int updateExpectedPriceByOrdersId(Long ordersId, Long expectedPrice){
        return ordersMapper.updateExpectedPriceByOrdersId(ordersId, expectedPrice);
    }

    public Optional<Long> findExpectedPriceByOrdersId(Long ordersId){
        return ordersMapper.findExpectedPriceByOrdersId(ordersId);
    }

    public int updateOrdersStatusToCompletePayment(Long ordersId){
        return ordersMapper.updateOrdersStatusToCompletePayment(ordersId, ConstOrderStatus.PAY_SUCCESS);
    }

    public int updateCouponListStatusToUsedCoupon(Long couponListId, Long ordersId){
        return ordersMapper.updateCouponListStatusToUsedCoupon(couponListId, ordersId, ConstCouponListStatus.USED);
    }

    public int addPoint(AddPointResponseDto pointDto){
        return ordersMapper.addPoint(pointDto);
    }

    public List<OrdersListResponseDto> findOrdersByMemberIdAndCri(Criteria cri, Long memberId){
        return ordersMapper.findOrdersByMemberIdAndCri(cri, memberId);
    }

    public List<OrdersListResponseDto> findOrdersFinishedByMemberIdAndCri(Criteria cri, Long memberId){
        return ordersMapper.findOrdersFinishedByMemberIdAndCri(cri, memberId);
    }


    public int getTotalCount(Long memberId) {
        return ordersMapper.getTotalCount(memberId, ConstOrderStatus.ORDERS_CANCEL);
    }

    public int updatePaymentinfoIdByOrdersId(Long paymentinfoId, Long ordersId){
        return ordersMapper.updatePaymentinfoIdByOrdersId(paymentinfoId, ordersId);
    }

    public int updatePriceNStatusNPaymentinfo(Long ordersFinalPrice, Long paymentinfoId, Long ordersId, int constOrderStatus){
        return ordersMapper.updatePriceNStatusNPaymentinfo(ordersFinalPrice, paymentinfoId,
                ordersId, constOrderStatus);
    }

    public int updatePriceNStatusNPaymentinfoForCommonDelivery(Long ordersFinalPrice, Long paymentinfoId, Long ordersId, int constOrderStatus){
        return ordersMapper.updatePriceNStatusNPaymentinfo(ordersFinalPrice, paymentinfoId,
                ordersId, constOrderStatus);
    }



    public PriceResponseDto findPricesByOrdersId(Long ordersId){
        PriceResponseDto priceResponseDto = ordersMapper.findPricesByOrdersId(ordersId)
                .orElseThrow(() -> new IllegalArgumentException("결제금액, 포인트, 쿠폰가격을 찾을 수 없습니다."));
        log.info("price=============={}", priceResponseDto);
        return priceResponseDto;
    }

    public int updatePointIdByOrdersId(Long pointId, Long ordersId){
        return ordersMapper.updatePointIdByOrdersId(pointId, ordersId);
    }

    public int updateCouponStatusNOrdersId(Long ordersId, Long couponListId){
        return ordersMapper.updateCouponStatusNOrdersId(ConstCouponListStatus.USED, ordersId, couponListId);
    }

    public Long findSubscriptionDiscountPrice(Long ordersId){
        return ordersMapper.findSubscriptionDiscountPrice(ordersId);
    }

    public int updateSubscriptionDiscountPrice(Long subscriptionDiscountPrice, Long ordersId){
        return ordersMapper.updateSubscriptionDiscountPrice(subscriptionDiscountPrice, ordersId);
    }

    public int findCountOfQuickDelivery(Long ordersId){
        return ordersMapper.findCountOfQuickDelivery(ordersId);
    }

    public List<CategoryForOrdersListDto> findCategoryByMemberId(Long memberId){
        List<CategoryForOrdersListDto> category = ordersMapper.findCategoryByMemberId(
                memberId, ConstOrderStatus.ORDERS_CANCEL, ConstOrderStatus.DELIVERY_SUCCESS);
        if(category == null || category.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        return category;
    }

    public List<CategoryForOrdersListDto> findCategoryFinishedByMemberId(Long memberId){
        List<CategoryForOrdersListDto> categoryFinished = ordersMapper.findCategoryFinishedByMemberId(
                memberId, ConstOrderStatus.ORDERS_CANCEL, ConstOrderStatus.DELIVERY_SUCCESS);
        if(categoryFinished == null || categoryFinished.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        return categoryFinished;
    }

    public List<OrdersForOrdersListDto> findOrders(Long memberId){
        List<OrdersForOrdersListDto> orders = ordersMapper.findOrders(
                memberId, ConstOrderStatus.ORDERS_CANCEL, ConstOrderStatus.DELIVERY_SUCCESS);
        if(orders == null || orders.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        return orders;
    }

    public List<OrdersForOrdersListDto> findOrdersFinished(Long memberId){
        List<OrdersForOrdersListDto> ordersFinished = ordersMapper.findOrdersFinished(
                memberId, ConstOrderStatus.ORDERS_CANCEL, ConstOrderStatus.DELIVERY_SUCCESS);
        if(ordersFinished == null || ordersFinished.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        return ordersFinished;
    }

    public Long findCouponListIdByOrdersId(Long ordersId){
        return ordersMapper.findCouponListIdByOrdersId(ordersId);
    }

    public int updateCouponList(Long couponListId){
        return ordersMapper.updateCouponList(ConstCouponListStatus.NOT_USED, couponListId);
    }

    public int updateRiderId(Long ordersId){
        return ordersMapper.updateRiderId(ordersId);
    }





}
