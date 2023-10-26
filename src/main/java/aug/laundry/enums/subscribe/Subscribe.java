package aug.laundry.enums.subscribe;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
public enum Subscribe {

    ONE(1, 5900),
    THREE(3, 14700),
    SIX(6, 27000),
    TWELVE(12, 49200);

    private final int selectMonth;
    private final int price;

    Subscribe(int selectMonth, int price) {
        this.selectMonth = selectMonth;
        this.price = price;
    }
}
