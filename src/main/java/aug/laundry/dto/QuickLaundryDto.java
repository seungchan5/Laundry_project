package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuickLaundryDto {

    private Long quickLaundryId;
    private String quickLaundryTakeDate;
    private Integer quickLaundryTakeTime;
    private Long ordersDetailId;

    public QuickLaundryDto() {
    }
}
