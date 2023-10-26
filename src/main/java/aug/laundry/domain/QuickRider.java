package aug.laundry.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuickRider {

    private Long quickRiderId;
    private String quickRiderEmail;
    private String quickRiderPassword;
    private String quickRiderName;
    private String quickRiderPhone;
    private Integer quickRiderZipcode;
    private String quickRiderAddress;
    private String workingArea;

    public QuickRider() {
    }
}
