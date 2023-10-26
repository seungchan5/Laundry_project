package aug.laundry.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeliveryImage {

    private Long deliveryImageId;
    private Long ordersId;
    private String deliveryImageUploadName;
    private String deliveryImageStoreName;

    public DeliveryImage() {
    }
}
