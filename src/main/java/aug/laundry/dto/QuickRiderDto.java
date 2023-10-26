package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuickRiderDto {
    private Long quickRiderId;
    private String quickRiderEmail;
    private String quickRiderPassword;
    private String quickRiderName;
    private String quickRiderPhone;
    private Integer quickRiderZipcode;
    private String quickRiderAddress;

    public QuickRiderDto(Long quickRiderId, String quickRiderEmail, String quickRiderPassword, String quickRiderName, String quickRiderPhone, Integer quickRiderZipcode, String quickRiderAddress) {
        this.quickRiderId = quickRiderId;
        this.quickRiderEmail = quickRiderEmail;
        this.quickRiderPassword = quickRiderPassword;
        this.quickRiderName = quickRiderName;
        this.quickRiderPhone = quickRiderPhone;
        this.quickRiderZipcode = quickRiderZipcode;
        this.quickRiderAddress = quickRiderAddress;
    }
}
