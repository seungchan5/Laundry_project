package aug.laundry.dto;

import aug.laundry.enums.orderStatus.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrdersForOrdersListDto {

    private Long ordersId;
    private String ordersDate;
    private OrderStatus orderStatus;
}
