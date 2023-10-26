package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScheduleDto {

    private String merchant_uid;
    private long schedule_at;
    private int amount;
    private String name;
}
