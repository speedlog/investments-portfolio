package pl.speedlog.investments.domain.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
public class BigDecimalHelper {

    private BigDecimalHelper() {
    }

    public static BigDecimal stripZeros(BigDecimal value) {
        if (value == null) {
            return value;
        }
        BigDecimal trimmed = value.stripTrailingZeros();
        if (trimmed.scale() < 0) {
            trimmed = trimmed.setScale(0, RoundingMode.HALF_EVEN);
        }
        return trimmed;
    }

}
