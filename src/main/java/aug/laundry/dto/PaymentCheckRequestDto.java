package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@ToString
public class PaymentCheckRequestDto {


    private Long couponListId;
    private Long couponPrice;
    private Long pointPrice;
    private String imp_uid;
    private String merchant_uid;
    private boolean imp_success;

    public PaymentCheckRequestDto(Long couponListId, Long couponPrice, Long pointPrice, String imp_uid, String merchant_uid, boolean imp_success) {
        this.couponListId = couponListId;
        this.couponPrice = couponPrice;
        this.pointPrice = pointPrice;
        this.imp_uid = imp_uid;
        this.merchant_uid = merchant_uid;
        this.imp_success = imp_success;
    }
}
