package aug.laundry.enums.category;

import java.util.function.Function;

public enum CategoryPriceCalculator {
    COMMON(value -> value),
    PASS(value -> Math.round((Long)value * 0.85));

    private Function<Long, Long> expression;

    CategoryPriceCalculator(Function expression) {
        this.expression = expression;
    }

    protected Long calculate(Long value) {
        return this.expression.apply(value);
    }

    public Float percent() {
        return this.expression.apply(100L) / 100.0f;
    }
}
