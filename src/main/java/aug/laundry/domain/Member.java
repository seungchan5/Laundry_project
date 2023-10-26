package aug.laundry.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Member {

    private Long memberId;
    private String memberAccount;
    private String memberPassword;
    private String memberName;
    private String memberPhone;
    private Integer memberZipcode;
    private String memberAddress;
    private String memberCreateDate;
    private String memberSocial;
    private Long subscriptionId;
    private String subscriptionExpireDate;
    private Long gradeId;
    private String memberRecentlyDate;
    private char memberDeleteStatus;
    private String memberMyInviteCode;
    private Long ordersDetailId;
    private String memberAddressDetails;
    private String memberInviteCode;

    public Member() {
    }
}
