package aug.laundry.dto;

import aug.laundry.enums.category.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
@ToString
public class DrycleaningResponseDto {

    private Category drycleaningCategory;
    private char drycleaningPossibility;
    private String drycleaningNotReason;
    @NumberFormat(pattern = "###,###")
    private Long drycleaningPrice;

    public DrycleaningResponseDto(Category drycleaningCategory, char drycleaningPossibility,
                                  String drycleaningNotReason, Long drycleaningPrice) {
        this.drycleaningCategory = drycleaningCategory;
        this.drycleaningPossibility = drycleaningPossibility;
        this.drycleaningNotReason = drycleaningNotReason;
        this.drycleaningPrice = drycleaningPrice;
    }
}
