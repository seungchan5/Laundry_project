package aug.laundry.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
@Data
@Component
public class ConfirmIdDto {
    @NotBlank(message = "memberName is required")
    private String memberName;

    @NotBlank(message = "phoneNumber is required")
    private String memberPhone;
    private String memberAccount;

}
