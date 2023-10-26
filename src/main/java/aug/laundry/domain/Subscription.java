package aug.laundry.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Subscription {

    private Long subscriptionId;
    private String subscriptionName;
    private Integer subscriptionPrice;
    private Integer subscriptionMonth;
    private Integer subscriptionDiscountPercent;

    public Subscription() {
    }
}
