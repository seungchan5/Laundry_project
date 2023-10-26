package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrdersDetailDto {

    private Long ordersDetailId;
    private Long ordersId;
    private Long memberId;

    public OrdersDetailDto() {
    }
}
