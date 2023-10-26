package aug.laundry.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Inspection {

    private Long inspectionId;
    private Long adminId;
    private char inspectionStatus;
    private Long ordersId;

    public Inspection() {
    }
}
