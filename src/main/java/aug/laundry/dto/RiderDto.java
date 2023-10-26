package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RiderDto {
    private Long riderId;
    private String riderEmail;
    private String riderPassword;
    private String riderName;
    private String riderPhone;
    private Integer riderZipcode;
    private String riderAddress;
    private String riderPossibleZipcode;

    public RiderDto(Long riderId, String riderEmail, String riderPassword, String riderName, String riderPhone, Integer riderZipcode, String riderAddress, String riderPossibleZipcode) {
        this.riderId = riderId;
        this.riderEmail = riderEmail;
        this.riderPassword = riderPassword;
        this.riderName = riderName;
        this.riderPhone = riderPhone;
        this.riderZipcode = riderZipcode;
        this.riderAddress = riderAddress;
        this.riderPossibleZipcode = riderPossibleZipcode;
    }
}
