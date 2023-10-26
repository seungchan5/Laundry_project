package aug.laundry.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Coupon {

    private Long couponId;
    private String couponName;
    private Integer couponType;
    private Integer couponPrice;
    private Integer couponPeriodOfUse;
    private Long gradeId;

    public Coupon() {
    }
}
