package aug.laundry.service;

import aug.laundry.dao.LaundryRepository;
import aug.laundry.dao.orders.OrdersDao;
import aug.laundry.dao.point.PointDao;
import aug.laundry.domain.Drycleaning;
import aug.laundry.domain.Repair;
import aug.laundry.dto.*;
import aug.laundry.enums.category.Category;
import aug.laundry.enums.orderStatus.OrderStatus;
import aug.laundry.enums.repair.RepairCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService {

    private final OrdersDao ordersDao;
    private final PointDao pointDao;
    private final LaundryRepository laundryRepository;

    public OrdersResponseDto findByOrdersId(Long ordersId){
        OrdersResponseDto ordersResponseDto = ordersDao.findByOrdersId(ordersId);

        Double commonLaundryWeight = ordersResponseDto.getCommonLaundryWeight();

        //생활빨래 계산로직
        if(commonLaundryWeight == null){
            ordersResponseDto.setCommonLaundryPrice(0L);
        }

        if(commonLaundryWeight != null){
            Category commonLaundry = Category.BASIC;
            Category commonAdditional = Category.ADDITIONAL;

            calculateCommonLaundry(ordersResponseDto, commonLaundryWeight, commonLaundry, commonAdditional);
        }

        return ordersResponseDto;
    }

    public static void calculateCommonLaundry(OrdersResponseDto ordersResponseDto, Double commonLaundryWeight, Category commonLaundry, Category commonAdditional) {
        if (commonLaundryWeight > 0 && commonLaundryWeight <= 10) {
            //생활빨래 0~10리터
            Long price = 1 * commonLaundry.getPrice();
            ordersResponseDto.setCommonLaundryPrice(price);

        } else if(commonLaundryWeight > 10 && commonLaundryWeight <=20){
            //생활빨래 10~20리터
            Long price = 2 * commonLaundry.getPrice();
            ordersResponseDto.setCommonLaundryPrice(price);

        } else if(commonLaundryWeight > 20){
            //생활빨래 20리터까지 계산
            Long price1 = 2 * commonLaundry.getPrice();
            //생활빨래 20리터 이후 가격 계산
            Long price2 = (long) Math.ceil((commonLaundryWeight - 20) / 10) * commonAdditional.getPrice();
            ordersResponseDto.setCommonLaundryPrice(price1 + price2);
        }
    }

    public Map<String, Object> findDryCleaningByOrdersId(Long ordersId){

        List<Drycleaning> dryList = ordersDao.findDryCleaningByOrdersId(ordersId);

        Map<String, Object> dryMap = new HashMap<>();

        if(dryList.isEmpty()) {
            dryMap.put("dryList", Collections.EMPTY_LIST);
            dryMap.put("totalDryPrice", 0L);
        } else{
            List<DrycleaningResponseDto> dryDtoList = MapToDryResponseDto(dryList);
            long totalDryPrice = dryDtoList.stream()
                    .filter(dry -> dry.getDrycleaningPossibility() == 'Y')
                    .mapToLong(dry -> dry.getDrycleaningPrice())
                    .sum();

            dryMap.put("dryList", dryDtoList);
            dryMap.put("totalDryPrice", totalDryPrice);
        }

        return dryMap;
    }

    private static List<DrycleaningResponseDto> MapToDryResponseDto(List<Drycleaning> dryList) {
        return dryList.stream()
                .map(dry -> new DrycleaningResponseDto(
                        Category.valueOf(dry.getDrycleaningCategory()),
                        dry.getDrycleaningPossibility(),
                        dry.getDrycleaningNotReason(),
                        Category.valueOf(dry.getDrycleaningCategory()).getPrice()
                )).collect(Collectors.toList());
    }

    public Map<String, Object> findRepairByOrdersId(Long ordersId){

        List<Repair> repairList = ordersDao.findRepairByOrdersId(ordersId);

        Map<String, Object> repairMap = new HashMap<>();


        if(repairList.isEmpty()){
            repairMap.put("repairList", Collections.EMPTY_LIST);
            repairMap.put("totalRepairPrice", 0L);
        } else {
            List<RepairResponseDto> repairDtoList = mapToRepairResponseDto(repairList);
            long totalRepairPrice = repairDtoList.stream()
                    .filter(repair -> repair.getRepairPossibility() == 'Y')
                    .mapToLong(repair -> repair.getRepairPrice())
                    .sum();

            repairMap.put("repairList", repairDtoList);
            repairMap.put("totalRepairPrice", totalRepairPrice);
        }

        return repairMap;
    }

    @NotNull
    private static List<RepairResponseDto> mapToRepairResponseDto(List<Repair> repairList) {
        return repairList.stream()
                .map(repair -> new RepairResponseDto(
                        aug.laundry.enums.repair.RepairCategory.valueOf(repair.getRepairCategory()),
                        repair.getRepairPossibility(),
                        repair.getRepairNotReason(),
                        aug.laundry.enums.repair.RepairCategory.valueOf(repair.getRepairCategory()).getPrice()
                )).collect(Collectors.toList());
    }

    public Integer findPointByMemberId(Long memberId){
        return pointDao.findByMemberId(memberId);
    }

    public boolean isQuickLaundry(Long ordersId){
        return ordersDao.isQuickLaundry(ordersId);
    }


    @Transactional
    public void updateExpectedNDiscountPriceByOrdersId(Long ordersId, Long expectedPrice, Long subscriptionDiscountPrice) {
        int result = ordersDao.updateExpectedNDiscountPriceByOrdersId(ordersId, expectedPrice, subscriptionDiscountPrice);
        if(result==0) {
            throw new IllegalArgumentException("예상금액이 업데이트 되지 않았습니다.");
        }
    }

    @Transactional
    public void updateExpectedPriceByOrdersId(Long ordersId, Long expectedPrice) {
        int result = ordersDao.updateExpectedPriceByOrdersId(ordersId, expectedPrice);
        if(result==0) {
            throw new IllegalArgumentException("예상금액이 업데이트 되지 않았습니다.");
        }
    }

    @Transactional
    public void updateOrdersStatusToCompletePayment(Long ordersId){
        int result = ordersDao.updateOrdersStatusToCompletePayment(ordersId);
        if(result==0) {
            throw new IllegalArgumentException("주문상태가 업데이트 되지 않았습니다.");
        }
    }

    @Transactional
    public void updateCouponListStatusToUsedCoupon(Long couponListId, Long ordersId){
        int result = ordersDao.updateCouponListStatusToUsedCoupon(couponListId, ordersId);
        if(result==0) {
            throw new IllegalArgumentException("쿠폰리스트상태가 업데이트 되지 않았습니다.");
        }
    }

    @Transactional
    public Long addPoint(Long memberId, Long pointStack, String pointStackReason){
        AddPointResponseDto pointDto = new AddPointResponseDto(memberId, pointStack, pointStackReason);
        int result = ordersDao.addPoint(pointDto);
        if(result==0) {
            throw new IllegalArgumentException("포인트 적립/사용이 업데이트 되지 않았습니다.");
        }
        log.info("pointDto={}", pointDto);
        return pointDto.getPointId();
    }

    public Long findExpectedPriceByOrdersId(Long ordersId){
        return ordersDao.findExpectedPriceByOrdersId(ordersId)
                .orElseThrow(() -> new IllegalArgumentException("예상금액이 존재하지 않습니다."));
    }

    public List<OrdersListResponseDto> findOrdersByMemberIdAndCri(Criteria cri, Long memberId){
        List<OrdersListResponseDto> ordersList = ordersDao.findOrdersByMemberIdAndCri(cri, memberId);

        if(ordersList == null || ordersList.isEmpty()){
            return Collections.EMPTY_LIST;
        } else {
            ordersList.stream()
                    .forEach(order -> {
                        order.setStatusEnum(
                                OrderStatus.valueOf("R"+order.getOrdersStatus()));
                    });
            return ordersList;
        }
    }

    public List<OrdersListResponseDto> findOrdersFinishedByMemberIdAndCri(Criteria cri, Long memberId){
        List<OrdersListResponseDto> ordersList = ordersDao.findOrdersFinishedByMemberIdAndCri(cri, memberId);

        if(ordersList == null || ordersList.isEmpty()){
            return Collections.EMPTY_LIST;
        } else {
            ordersList.stream()
                    .forEach(order -> {
                        order.setStatusEnum(
                                OrderStatus.valueOf("L"+order.getOrdersStatus()));
                    });
            return ordersList;
        }
    }

    public int getTotalCount(Long memberId){
        return ordersDao.getTotalCount(memberId);
    }

    @Transactional
    public void updatePaymentinfoIdByOrdersId(Long paymentinfoId, Long ordersId){
        int result = ordersDao.updatePaymentinfoIdByOrdersId(paymentinfoId, ordersId);
        if(result==0) {
            throw new IllegalArgumentException("주문테이블의 paymentinfoId가 업데이트 되지 않았습니다.");
        }
    }

    @Transactional
    public void updatePriceNStatusNPaymentinfo(Long ordersFinalPrice, Long paymentinfoId, Long ordersId, int constOrderStatus){
        int result = ordersDao.updatePriceNStatusNPaymentinfo(ordersFinalPrice, paymentinfoId, ordersId, constOrderStatus);
        if(result==0) {
            throw new IllegalArgumentException("주문테이블의 최종결제금액, 주문상태, 결제정보Id, QuickRiderId가 업데이트 되지 않았습니다.");
        }
    }

    @Transactional
    public void updatePriceNStatusNPaymentinfoForCommonDelivery(Long ordersFinalPrice, Long paymentinfoId, Long ordersId, int constOrderStatus){
        int result = ordersDao.updatePriceNStatusNPaymentinfo(ordersFinalPrice, paymentinfoId, ordersId, constOrderStatus);
        if(result==0) {
            throw new IllegalArgumentException("주문테이블의 최종결제금액, 주문상태, 결제정보Id, RiderId가 업데이트 되지 않았습니다.");
        }
    }


    public PriceResponseDto findPricesByOrdersId(Long ordersId){
        return ordersDao.findPricesByOrdersId(ordersId);
    }

    @Transactional
    public void updatePointIdByOrdersId(Long pointId, Long ordersId){
        int result = ordersDao.updatePointIdByOrdersId(pointId, ordersId);
        if(result==0) {
            throw new IllegalArgumentException("주문테이블의 pointId가 업데이트 되지 않았습니다.");
        }
    }
    
    @Transactional
    public void updateCouponStatusNOrdersId(Long ordersId, Long couponListId){
        int result = ordersDao.updateCouponStatusNOrdersId(ordersId, couponListId);
        if(result==0) {
            throw new IllegalArgumentException("CouponList테이블의 쿠폰상태가 업데이트 되지 않았습니다.");
        }
    }

    public Long findSubscriptionDiscountPrice(Long ordersId){
        return ordersDao.findSubscriptionDiscountPrice(ordersId);
    }

    @Transactional
    public void updateSubscriptionDiscountPrice(Long subscriptionDiscountPrice, Long ordersId){
        int result = ordersDao.updateSubscriptionDiscountPrice(subscriptionDiscountPrice, ordersId);

        if(result==0) {
            throw new IllegalArgumentException("구독할인금액이 업데이트 되지 않았습니다.");
        }
    }

    public int findCountOfQuickDelivery(Long ordersId){
        return ordersDao.findCountOfQuickDelivery(ordersId);
    }

    public List<CategoryForOrdersListDto> findCategoryByMemberId(Long memberId){
        List<CategoryForOrdersListDto> category = ordersDao.findCategoryByMemberId(memberId);
        if(category != null & !category.isEmpty()){
            setEnglishCategoryToKorean(category);
        }

        return category;
    }

    public List<CategoryForOrdersListDto> findCategoryFinishedByMemberId(Long memberId){
        List<CategoryForOrdersListDto> category = ordersDao.findCategoryFinishedByMemberId(memberId);
        if(category != null & !category.isEmpty()){
            setEnglishCategoryToKorean(category);
        }

        return category;
    }

    private static void setEnglishCategoryToKorean(List<CategoryForOrdersListDto> category) {
        category.stream()
                .forEach(dto -> {

                    String c = dto.getCategory();

                    // 'OVERHANGING (REPAIR) 외 2건' 에서 REPAIR
                    String cg = c.substring(c.indexOf("(")+1, c.indexOf(")"));

                    // 'OVERHANGING (REPAIR) 외 2건' 에서 OVERHANGING
                    String first = c.substring(0, c.indexOf(" "));

                    if("DRYCLEANING".equals(cg)){

                        Category category1 = Category.valueOf(first);
                        String title = category1.getTitle();

                        String str = c.replace(first, title + " 드라이클리닝");

                        String temp1 = str.substring(0, str.indexOf("("));
                        int i = c.indexOf(")");
                        String temp2 = "";

                        if(i!=-1){
                            temp2 = c.substring(c.indexOf(")") + 1);

                        }

                        String result = temp1.concat(temp2);
                        dto.setCategory(result);

                    }
                    else if("REPAIR".equals(cg)){
                        RepairCategory repair = RepairCategory.valueOf(first);
                        String title = repair.getTitle();
                        String str = c.replace(first, title + " 수선");

                        String temp1 = str.substring(0, str.indexOf("("));
                        int i = c.indexOf(")");
                        String temp2 = "";

                        if(i!=-1){
                            temp2 = c.substring(c.indexOf(")") + 1);

                        }

                        String result = temp1.concat(temp2);
                        dto.setCategory(result);
                    }
                    else if("COMMON".equals(cg)){
                        Category category1 = Category.valueOf(first);
                        String title = category1.getTitle();
                        c = c.replace(first, title);
                        String result = c.substring(0, c.indexOf("("));

                        dto.setCategory(result);
                    }
                });
    }

    public List<OrdersForOrdersListDto> findOrders(Long memberId){
        return ordersDao.findOrders(memberId);
    }

    public List<OrdersForOrdersListDto> findOrdersFinished(Long memberId){
        return ordersDao.findOrdersFinished(memberId);
    }

    public List<MyCoupon> getCoupon(Long memberId) {
        return laundryRepository.getCoupon2(memberId);
    }

    public Long findCouponListIdByOrdersId(Long ordersId){
        return ordersDao.findCouponListIdByOrdersId(ordersId);
    }

    public void updateCouponList(Long couponListId){
        int result = ordersDao.updateCouponList(couponListId);
        if(result==0) {
            throw new IllegalArgumentException("CouponList테이블의 쿠폰상태와 주문아이디가 업데이트 되지 않았습니다.");
        }
    }

    public void updateRiderId(Long ordersId){
        int result = ordersDao.updateRiderId(ordersId);
        if(result==0) {
            throw new IllegalArgumentException("Orders테이블의 riderId가 업데이트 되지 않았습니다.");
        }
    }
}
