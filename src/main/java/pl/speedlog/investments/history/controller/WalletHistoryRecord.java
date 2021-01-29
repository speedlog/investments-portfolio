package pl.speedlog.investments.history.controller;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
@Builder
@Getter
class WalletHistoryRecord {

    private final LocalDate date;
    private final BigDecimal numberOfUnits;
    private final BigDecimal unitPrice;
    private final BigDecimal commission;
    private final boolean isPurchase;

    public String getNumberOfUnits() {
        return numberOfUnits
                .setScale(6, RoundingMode.HALF_EVEN)
                .stripTrailingZeros()
                .toPlainString();
    }

    public String getUnitPrice() {
        return unitPrice
                .setScale(6, RoundingMode.HALF_EVEN)
                .stripTrailingZeros()
                .toPlainString();
    }

    public String getCommission() {
        return commission
                .setScale(2, RoundingMode.HALF_EVEN)
                .stripTrailingZeros()
                .toPlainString();
    }

    public String getValue() {
        return numberOfUnits.multiply(unitPrice).subtract(commission)
                .setScale(2, RoundingMode.HALF_EVEN)
                .stripTrailingZeros()
                .toPlainString();
    }

    public boolean isPurchase() {
        return isPurchase;
    }

}
