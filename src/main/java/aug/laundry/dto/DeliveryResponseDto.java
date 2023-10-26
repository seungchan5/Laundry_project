package aug.laundry.dto;

import aug.laundry.enums.category.Delivery;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeliveryResponseDto {

    private Delivery delivery;
    private boolean deliveryStatus;  //Lombok과 타임리프에서의 문법 차이점 때문에 boolean 타입이지만 Status를 붙임

    public DeliveryResponseDto(Delivery delivery) {
        this.delivery = delivery;
    }

    public DeliveryResponseDto(Delivery delivery, boolean deliveryStatus) {
        this.delivery = delivery;
        this.deliveryStatus = deliveryStatus;
    }
}
