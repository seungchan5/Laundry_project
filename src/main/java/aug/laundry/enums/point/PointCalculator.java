package aug.laundry.enums.point;

import java.util.function.Function;

public enum PointCalculator {

    COMMON(value -> Math.round((Long)value * 0.01)),
    PASS(value -> Math.round((Long)value * 0.03));

    private Function<Long, Long> expression;

    PointCalculator(Function expression) {
        this.expression = expression;
    }

    public Long calculate(Long value) {
        return this.expression.apply(value);
    }
}
