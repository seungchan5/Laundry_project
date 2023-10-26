package aug.laundry.enums.category;

import aug.laundry.enums.point.PointCalculator;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberShip {
    private Pass check;

    public MemberShip(Pass check) {
        this.check = check;
    }

    public Long apply(Long price) {
        if (this.check == Pass.PASS) {
            return CategoryPriceCalculator.PASS.calculate(price);
        } else {
            return CategoryPriceCalculator.COMMON.calculate(price);
        }
    }

    public Long applyPoint(Long price){
        if (this.check == Pass.PASS) {
            return PointCalculator.PASS.calculate(price);
        } else {
            return PointCalculator.COMMON.calculate(price);
        }
    }
}