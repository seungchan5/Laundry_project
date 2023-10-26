package aug.laundry.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Drycleaning {

    private Long drycleaningId;
    private Long ordersDetailId;
    private String drycleaningRequest;
    private String drycleaningCategory;
    private char drycleaningPossibility;
    private String drycleaningNotReason;


    public Drycleaning() {
    }

}
