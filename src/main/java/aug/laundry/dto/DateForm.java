package aug.laundry.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class DateForm {

    private String pickup;
    private String pickupTime;

    private String returns;
    private String returnsTime;

    public DateForm() {
        // 현재날짜 기준 18시 이전이라면 오늘 23시 수거, 18시 이후라면 다음날 23시 수거
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime nowDate = LocalDateTime.now();
        LocalTime localTime = nowDate.toLocalTime();
        // 22시 이전 주문 - 당일 23시 수거 - 2일뒤 07시 배송 1번
        // 09시 이전 주문 - 당일 10시 수거 - 2일뒤 18시 배송 2번

        if (localTime.isBefore(LocalTime.of(22, 0)) && localTime.isAfter(LocalTime.of(9, 0))) {
            // 09시 ~ 22시 1번
            this.pickup = nowDate.with(LocalTime.of(23, 0)).format(dateTimeFormatter);
            this.pickupTime = "23";
            this.returns = nowDate.plusDays(2).with(LocalTime.of(7, 0)).format(dateTimeFormatter);
            this.returnsTime = "07";
        } else {
            // 22시 ~ 09시 2번
            this.pickup = nowDate.with(LocalTime.of(10, 0)).format(dateTimeFormatter);
            this.pickupTime = "10";
            this.returns = nowDate.plusDays(2).with(LocalTime.of(18, 0)).format(dateTimeFormatter);
            this.pickupTime = "18";
        }

    }
}
