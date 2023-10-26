package aug.laundry.dao;

import aug.laundry.domain.CouponList;
import aug.laundry.domain.Orders;
import aug.laundry.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LaundryMapper {

    OrderInfoDB firstInfo(@Param("memberId") Long memberId, @Param("ordersDetailId") Long ordersDetailId);

    Integer isQuick(Long memberId);
    Integer isDry(Long memberId);
    Integer isCommon(Long memberId);
    Integer isRepair(Long memberId);

    List<MyCoupon> getCoupon(Long memberId);

    Address getAddress(Long memberId);

    List<String> getDry(@Param("memberId") Long memberId, @Param("ordersDetailId") Long ordersDetailId);

    List<String> getRepair(@Param("memberId") Long memberId, @Param("ordersDetailId") Long ordersDetailId);

    Integer isPass(Long memberId);

    CouponList validCoupon(@Param("memberId") Long memberId, @Param("couponListId") Long couponListId);

    Integer useCoupon(@Param("memberId") Long memberId, @Param("couponListId") Long couponListId, @Param("ordersId") Long ordersId);

    void insert(Orders orders);

    Long getCouponDiscount(@Param("memberId") Long memberId, @Param("couponListId") Long couponListId);

    Long check(@Param("memberId") Long memberId, @Param("ordersDetailId") Long ordersDetailId);

    void removeOrdersDetail(Long memberId);

    void createOrdersDetail(Long memberId);

    void removeDrycleaning(Long ordersDetailId);

    void removeCommon(Long ordersDetailId);

    void removeRepair(Long ordersDetailId);

    void insertDryCleaning(@Param("ordersDetailId") Long ordersDetailId, @Param("category") String title);

    List<OrderDrycleaning> reloadDrycleaning(Long orderDetailId);

    List<OrderRepair> reloadRepair(Long orderDetailId);

    List<String> getRepairImage(Long repairId);

    void insertRepair(InsertRepairDto insertRepairDto);

    List<Long> getRepairId(Long ordersDetailId);


    void removeRepairImages(Long repairId);


    List<String> getRepairImageStoreName(Long repairId);

    void insertCommon(Long ordersDetailId);

    void insertQuickLaundry(Long ordersDetailId);

    void removeQuickLaundry(Long ordersDetailId);

    void updateOrdersDetail(@Param("ordersId") Long ordersId, @Param("ordersDetailId") Long ordersDetailId);

    void insertInspection(Long ordersId);

    List<Long> findByRepairId(Long ordersDetailId);
  
    List<MyCoupon> getCoupon2(Long memberId);
  
}
