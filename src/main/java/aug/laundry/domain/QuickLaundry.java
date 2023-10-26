package aug.laundry.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuickLaundry {

    private Long quickLaundryId;
    private String quickLaundryTakeDate;
    private Integer quickLaundryTakeTime;
    private Long ordersDetailId;

    public QuickLaundry() {
    }
}
