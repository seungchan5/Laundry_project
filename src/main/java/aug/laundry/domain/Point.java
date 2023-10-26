package aug.laundry.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Point {

    private Long pointId;
    private Long memberId;
    private Integer pointStack;
    private Integer pointNow;
    private String pointStackReason;
    private String pointStackDate;

    public Point() {
    }
}

