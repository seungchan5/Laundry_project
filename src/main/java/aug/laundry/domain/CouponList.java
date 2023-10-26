package aug.laundry.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CouponList {

    private Long couponListId;
    private Long memberId;
    private Long couponId;
    private String couponListCreateDate;
    private Integer couponListStatus;
    private Long ordersId;
    private String couponEndDate;

    public CouponList() {
    }
}
