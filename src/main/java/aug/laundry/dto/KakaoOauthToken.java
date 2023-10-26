package aug.laundry.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class KakaoOauthToken {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;

}
