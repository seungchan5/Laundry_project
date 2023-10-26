package aug.laundry.dto;


import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;


@Data
@Component
public class MemberDto {

    private Long memberId;

    @NotBlank(message = "memberAccount is required")
    private String memberAccount;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,15}$", message = "비밀번호는 대소문자, 숫자, 특수문자를 포함한 8~15자여야 합니다.")
    private String memberPassword;

    @NotBlank(message = "memberName is required")
    private String memberName;

    @NotNull(message = "memberPhone is required")
    private String memberPhone;

    @NotNull(message = "memberZipcode is required")
    @Min(value = 0)
    @Max(value = 99999)
    private Integer memberZipcode;

    @NotBlank(message = "memberAddress is required")
    private String memberAddress;

    @NotBlank(message = "memberAddressDetails is required")
    private String memberAddressDetails;

    private String memberCreateDate;
    private String memberSocial;
    private Long subscriptionId;
    private String subscriptionExpireDate;
    private Long gradeId;
    private String memberRecentlyDate;
    private char memberDeleteStatus;
    private String memberMyInviteCode;
    private String memberInviteCode;
    private String sessionId;
    private String sessionLimit;

    // 기본 생성자 추가
    public MemberDto() {

    }

    public MemberDto(Long memberId, String memberAccount, String memberPassword, String memberName, String memberPhone, Integer memberZipcode, String memberAddress, String memberCreateDate, String memberSocial, Long subscriptionId, String subscriptionExpireDate, Long gradeId, String memberRecentlyDate, char memberDeleteStatus, String memberMyInviteCode, String memberAddressDetails, String memberInviteCode, String sessionId, String sessionLimit) {
        this.memberId = memberId;
        this.memberAccount = memberAccount;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberPhone = memberPhone;
        this.memberZipcode = memberZipcode;
        this.memberAddress = memberAddress;
        this.memberCreateDate = memberCreateDate;
        this.memberSocial = memberSocial;
        this.subscriptionId = subscriptionId;
        this.subscriptionExpireDate = subscriptionExpireDate;
        this.gradeId = gradeId;
        this.memberRecentlyDate = memberRecentlyDate;
        this.memberDeleteStatus = memberDeleteStatus;
        this.memberMyInviteCode = memberMyInviteCode;
        this.memberAddressDetails = memberAddressDetails;
        this.memberInviteCode = memberInviteCode;
        this.sessionId = sessionId;
        this.sessionLimit = sessionLimit;
    }







}
