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
public class UnsoldSummary {

    private final BigDecimal units;
    private final BigDecimal payment;
    private final BigDecimal averagePriceUnit;
    private final BigDecimal todayPrice;
    private final BigDecimal commision;
    private final BigDecimal profitLoss;
    private final BigDecimal profitLossPercentage;
    private final BigDecimal profitLossPerYear;

    public UnsoldSummary(List<UnsoldWalletRecord> unsoldWalletRecords) {
        units = unsoldWalletRecords.stream()
                .map(UnsoldWalletRecord::getUnits)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        payment = unsoldWalletRecords.stream()
                .map(UnsoldWalletRecord::getPurchaseValue)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        averagePriceUnit = unsoldWalletRecords.stream()
                .map(UnsoldWalletRecord::getPurchaseUnitPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .divide(BigDecimal.valueOf(unsoldWalletRecords.size()), MathContext.DECIMAL128);

        todayPrice = unsoldWalletRecords.stream().findAny().map(UnsoldWalletRecord::getDisposalUnitPrice).orElse(BigDecimal.ZERO);

        commision = unsoldWalletRecords.stream()
                .map(UnsoldWalletRecord::getSummaryCommision)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);


        profitLoss = unsoldWalletRecords.stream()
                .map(UnsoldWalletRecord::getProfitLoss)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        profitLossPercentage = unsoldWalletRecords.stream()
                .map(UnsoldWalletRecord::getProfitLossPercentage)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .divide(BigDecimal.valueOf(unsoldWalletRecords.size()), MathContext.DECIMAL128);

        profitLossPerYear = unsoldWalletRecords.stream()
                .map(UnsoldWalletRecord::getProfitLossPercentagePerYear)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .divide(BigDecimal.valueOf(unsoldWalletRecords.size()), MathContext.DECIMAL128);
    }

    public BigDecimal getUnits() {
        return BigDecimalHelper.stripZeros(units);
    }

    public BigDecimal getPayment() {
        return BigDecimalHelper.stripZeros(payment);
    }

    public BigDecimal getAveragePriceUnit() {
        return BigDecimalHelper.stripZeros(averagePriceUnit);
    }

    public BigDecimal getTodayPrice() {
        return BigDecimalHelper.stripZeros(todayPrice);
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
