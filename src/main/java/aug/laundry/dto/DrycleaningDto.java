package aug.laundry.dto;

import lombok.Data;

@Data
public class DrycleaningDto {

    private Long drycleaningId ;
    private Long  ordersDetailId;
    private String drycleaningRequest;
    private String drycleaningCategory;
    private char drycleaningPossibility;
    private String drycleaningNotReason;

    public DrycleaningDto(){

    }
}
