package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
@ToString
public class OrdersResponseDto {

    private String ordersDate;
    private Long ordersId;
    private String ordersPickupDate;
    private String ordersReturnDate;
    private String ordersAddress;
    private String ordersAddressDetails;
    private String ordersRequest;
    private Double commonLaundryWeight;
    private Long commonLaundryId;
    @NumberFormat(pattern = "###,###")
    private Long commonLaundryPrice;
    private Long memberId;
    private Integer ordersStatus;

}
