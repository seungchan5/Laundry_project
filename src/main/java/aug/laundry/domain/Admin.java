package aug.laundry.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Admin {

    private Long adminId;
    private String adminEmail;
    private String adminPassword;
    private String adminName;
    private Integer adminPhone;


    public Admin() {
    }
}
