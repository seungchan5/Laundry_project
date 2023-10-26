package aug.laundry.dao.payment;

import aug.laundry.domain.Drycleaning;
import aug.laundry.domain.Paymentinfo;
import aug.laundry.domain.Repair;
import aug.laundry.dto.CouponCheckDto;
import aug.laundry.dto.OrdersResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PaymentMapper {

    CouponCheckDto findCouponByCouponListId(Long couponListId);

    void savePaymentInfo(@Param("paymentinfo") Paymentinfo paymentinfo, @Param("constPaymentStatus") int constPaymentStatus);

    Paymentinfo findPaymentinfoByPaymentinfoId(Long paymentinfoId);

    int updateRefundInfoBypaymentinfoId(@Param("constPaymentStatus") int constPaymentStatus,
            @Param("paymentinfoId") Long paymentinfoId, @Param("errorMessage") String errorMessage);

    Optional<Paymentinfo> findPaymentinfoByImpUid(String impUid);

}
