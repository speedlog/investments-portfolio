package pl.speedlog.investments.domain.calculator;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
@Getter
@EqualsAndHashCode
public class SoldSummary {

    private final BigDecimal units;
    private final BigDecimal commision;
    private final BigDecimal profitLoss;
    private final BigDecimal profitLossPercentage;
    private final BigDecimal profitLossPerYear;

    SoldSummary(final List<WalletRecord> walletRecords) {
        units = walletRecords.stream()
                .map(WalletRecord::getUnits)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        commision = walletRecords.stream()
                    .map(WalletRecord::getSummaryCommision)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);

        profitLoss = walletRecords.stream()
                    .map(WalletRecord::getProfitLoss)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);

        profitLossPercentage = walletRecords.stream()
                                .map(WalletRecord::getProfitLossPercentage)
                                .reduce(BigDecimal::add)
                                .orElse(BigDecimal.ZERO)
                                .divide(BigDecimal.valueOf(walletRecords.size()), MathContext.DECIMAL128);

        profitLossPerYear = walletRecords.stream()
                            .map(WalletRecord::getProfitLossPercentagePerYear)
                            .reduce(BigDecimal::add)
                            .orElse(BigDecimal.ZERO)
                            .divide(BigDecimal.valueOf(walletRecords.size()), MathContext.DECIMAL128);
    }

    public BigDecimal getUnits() {
        return BigDecimalHelper.stripZeros(units);
    }

    public BigDecimal getCommision() {
        return BigDecimalHelper.stripZeros(commision);
    }

    public BigDecimal getProfitLoss() {
        return BigDecimalHelper.stripZeros(profitLoss);
    }

    public BigDecimal getProfitLossPercentage() {
        return BigDecimalHelper.stripZeros(profitLossPercentage);
    }

    public BigDecimal getProfitLossPerYear() {
        return BigDecimalHelper.stripZeros(profitLossPerYear);
    }
}
