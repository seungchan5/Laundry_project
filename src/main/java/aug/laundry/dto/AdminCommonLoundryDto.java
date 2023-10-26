package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AdminCommonLoundryDto {
    @NotNull
    private Long commonLaundryId;
    @DecimalMin(value = "0.0", message = "세탁물 무게는 0L이상이어야 합니다.")
    private Double commonLaundryWeight;
    private Long ordersDetailId;

    public AdminCommonLoundryDto() {

    }
}
