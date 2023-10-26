package aug.laundry.enums.category;

import lombok.Getter;

@Getter
public enum Delivery {

    COMMON_DELIVERY("일반배송", 3000L),
    QUICK_DELIVERY("빠른배송", 5000L);

    private String title;
    private Long price;


    Delivery(String title, Long price) {
        this.title = title;
        this.price = price;
    }

}
