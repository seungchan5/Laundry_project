package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class AdminInspectionDto {
    private Long ordersId;
    private String ordersDate;
    private Long quickLaundryId;
    private String ordersRequest;
    private Long inspectionId;
    private Long ordersDetailId;

    public AdminInspectionDto() {

    }

}
