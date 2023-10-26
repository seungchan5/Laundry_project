package aug.laundry.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo {

    private boolean isQuick;
    private boolean isDry;
    private boolean isCommon;
    private boolean isRepair;

}
