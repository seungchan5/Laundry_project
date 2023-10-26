package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubscriptionPayDto {
    private Long subscriptionPayId;
    private int selectMonth;
    private String merchantUid;
    private String merchantUidRe; // 다음번 예약 uid
    private Long customerUid;
    private int amount;
    private String payDate;
    private char subscriptionStatus;
    private String impUid;
    private int repayCount;
    private String failReason;
    private String subscriptionExpireDate;
    private int amountToBepay;
    private String payStatus;

    public SubscriptionPayDto() {

    }
}
