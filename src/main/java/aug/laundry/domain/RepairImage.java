package aug.laundry.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RepairImage {

    private Long repairImageId;
    private Long repairId;
    private String repairImageUploadName;
    private String repairImageStoreName;

    public RepairImage() {
    }
}
