package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminDto {
    private Long adminId;
    private String adminEmail;
    private String adminPassword;
    private String adminName;
    private Integer adminPhone;

    public AdminDto(Long adminId, String adminEmail, String adminPassword, String adminName, Integer adminPhone) {
        this.adminId = adminId;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.adminName = adminName;
        this.adminPhone = adminPhone;
    }
}
