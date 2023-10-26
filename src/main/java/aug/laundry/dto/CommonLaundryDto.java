package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommonLaundryDto {

    private Long commonLaundryId;
    private Double commonLaundryWeight;
    private Long ordersDetailId;

    public CommonLaundryDto() {
    }
}
