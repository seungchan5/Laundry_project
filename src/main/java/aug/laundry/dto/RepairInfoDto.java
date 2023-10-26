package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class RepairInfoDto {

    private Long repairId;
    private Long ordersDetailId;
    private String repairRequest;
    private String repairCategory;
    private char repairPossibility;
    private String repairNotReason;
    private List<String> repairImageStoreName;



    public RepairInfoDto() {

    }
}
