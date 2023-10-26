package aug.laundry.dto;

import aug.laundry.enums.repair.RepairCategory;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderRepair {

    private Long repairId;
    private Long ordersDetailId;
    private String repairRequest;
    private RepairCategory repairCategory;
    private Character repairPossibility;
    private String repairNotReason;

}