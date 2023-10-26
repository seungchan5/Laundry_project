package aug.laundry.dto;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InsertRepairDto {

    private Long repairId;
    private Long ordersDetailId;
    private String request;
    private String category;
}
