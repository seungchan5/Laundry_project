package aug.laundry.dto;

import lombok.Data;

@Data
public class KakaoProfile {
    public Long id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;

    @Data
    class Properties {
        public String nickname;
    }

    @Data
    public class KakaoAccount{
        public Boolean profile_nickname_needs_agreement;
        public Profile profile;
        public Boolean name_needs_agreement;
        public String name;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;
        public Boolean has_phone_number;
        public Boolean phone_number_needs_agreement;
        public String phone_number;


        @Data
        class Profile {
            String nickname;
        }
    }
}
