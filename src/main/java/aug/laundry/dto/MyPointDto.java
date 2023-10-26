package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MyPointDto {

  private Long pointId;
  private Long pointStack;
  private String pointStackReason;
  private String pointStackDate;
  private Long pointNow;

  public MyPointDto() {
  }
}
