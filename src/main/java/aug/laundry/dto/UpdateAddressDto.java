package aug.laundry.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAddressDto {

  @NotBlank
  private String memberZipcode;

  @NotBlank
  private String memberAddress;

  @NotBlank
  private String memberAddressDetails;

}
