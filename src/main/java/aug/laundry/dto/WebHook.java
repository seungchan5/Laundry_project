package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@ToString
public class WebHook {

    private String imp_uid;
    private String merchant_uid;
    private String status;

}
