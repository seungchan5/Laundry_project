package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MySubscribeMonthsDto {

  private Long subscriptionId;
  private String subscriptionExpireDate;
}
