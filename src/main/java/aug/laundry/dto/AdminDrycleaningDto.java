package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AdminDrycleaningDto {

    @NotNull
    private Long drycleaningId;
    private Long ordersDetailId;
    private String drycleaningRequest;
    private String drycleaningCategory;
    @NotNull
    private char drycleaningPossibility;
    private String drycleaningNotReason;

    public AdminDrycleaningDto() {

    }


}
