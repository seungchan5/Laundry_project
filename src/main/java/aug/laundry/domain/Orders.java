package aug.laundry.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Orders {

    private Long ordersId;
    private Long memberId;
    private String ordersDate;
    private String ordersAddress;
    private String ordersAddressDetails;
    private String ordersInfo;
    private String ordersRequest;
    private Integer ordersPay;
    private String ordersPickup;
    private Integer ordersExpectedPrice;
    private Integer ordersFinalPrice;
    private Integer ordersStatus;
    private Long riderId;
    private Long quickRiderId;
    private String ordersPickupDate;
    private String ordersReturnDate;
    private Long pointId;

    public Orders() {
    }
}
