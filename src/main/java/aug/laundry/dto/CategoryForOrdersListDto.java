package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryForOrdersListDto {

    private Long ordersId;
    private String category;

}
