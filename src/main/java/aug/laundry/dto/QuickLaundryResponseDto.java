package aug.laundry.dto;

import aug.laundry.enums.category.CategoryOption;
import aug.laundry.enums.category.Delivery;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
@ToString
public class QuickLaundryResponseDto {

    private Delivery quickLaundry;
    private boolean quickLaundryStatus;  //Lombok과 타임리프에서의 문법 차이점 때문에 boolean 타입이지만 Status를 붙임

    public QuickLaundryResponseDto(Delivery quickLaundry, boolean quickLaundryStatus) {
        this.quickLaundry = quickLaundry;
        this.quickLaundryStatus = quickLaundryStatus;
    }
}
