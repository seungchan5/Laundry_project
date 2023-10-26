package aug.laundry.dto;

import aug.laundry.enums.category.Category;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDrycleaning {

    private Category category;
    private Integer amount;

    public OrderDrycleaning(Category category, Integer amount) {
        this.category = category;
        this.amount = amount;
    }
}
