package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddPointResponseDto {

    private Long pointId;
    private Long memberId;
    private Long pointStack;
    private String pointStackReason;



    public AddPointResponseDto(Long memberId, Long pointStack, String pointStackReason) {
        this.memberId = memberId;
        this.pointStack = pointStack;
        this.pointStackReason = pointStackReason;
    }
}
