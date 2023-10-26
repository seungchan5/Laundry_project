package aug.laundry.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePhoneDto {

  @Pattern(regexp =  "(01)([016789]{1})(-)([0-9]{3,4})(-)([0-9]{4})")
  @NotBlank
  private String memberPhone;



}
