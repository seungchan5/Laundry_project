package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PriceResponseDto {

    private Integer pointStack;
    private Long couponPrice;
    private Long ordersFinalPrice;

}
