package aug.laundry.dto;

import aug.laundry.enums.repair.RepairCategory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
@ToString
public class RepairResponseDto {

    private RepairCategory repairCategory;
    private char repairPossibility;
    private String repairNotReason;
    @NumberFormat(pattern = "###,###")
    private Long repairPrice;

    public RepairResponseDto(RepairCategory repairCategory, char repairPossibility, String repairNotReason, Long repairPrice) {
        this.repairCategory = repairCategory;
        this.repairPossibility = repairPossibility;
        this.repairNotReason = repairNotReason;
        this.repairPrice = repairPrice;
    }
}
