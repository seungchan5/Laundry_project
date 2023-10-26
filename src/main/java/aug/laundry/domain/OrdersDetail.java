package aug.laundry.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrdersDetail {

    private Long ordersDetailId;
    private Long ordersId;

    public OrdersDetail() {
    }
}
