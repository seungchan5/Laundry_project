package aug.laundry.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDB {

    private Long isQuick;
    private Long isDry;
    private Long isCommon;
    private Long isRepair;

}
