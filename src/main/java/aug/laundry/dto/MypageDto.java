package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MypageDto {

  private Long memberId;
  private String memberName;
  private String memberPhone;
  private String memberZipcode;
  private String memberAddress;
  private Long subscriptionId;
  private String subscriptionExpireDate;
  private Long gradeId;
  private String memberMyInviteCode;
  private String memberAddressDetails;

  public MypageDto(){

  }

  public MypageDto(Long memberId, String memberName, String memberPhone, String memberZipcode, String memberAddress, Long subscriptionId, String subscriptionExpireDate, Long gradeId, String memberMyInviteCode, String memberAddressDetails) {
    this.memberId = memberId;
    this.memberName = memberName;
    this.memberPhone = memberPhone;
    this.memberZipcode = memberZipcode;
    this.memberAddress = memberAddress;
    this.subscriptionId = subscriptionId;
    this.subscriptionExpireDate = subscriptionExpireDate;
    this.gradeId = gradeId;
    this.memberMyInviteCode = memberMyInviteCode;
    this.memberAddressDetails = memberAddressDetails;
  }
}
