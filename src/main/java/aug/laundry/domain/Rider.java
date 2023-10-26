package aug.laundry.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Rider {

    private Long riderId;
    private String riderEmail;
    private String riderPassword;
    private String riderName;
    private String riderPhone;
    private Integer riderZipcode;
    private String riderAddress;
    private String riderPossibleZipcode;
    private String workingArea;

    public Rider() {
    }
}
