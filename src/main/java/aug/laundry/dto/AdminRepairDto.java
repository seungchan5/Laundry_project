package aug.laundry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AdminRepairDto {

    @NotNull
    private Long repairId;
    private Long ordersDetailId;
    private String repairRequest;
    private String repairCategory;
    @NotNull
    private char repairPossibility;
    private String repairNotReason;

    public AdminRepairDto() {

    }

}
