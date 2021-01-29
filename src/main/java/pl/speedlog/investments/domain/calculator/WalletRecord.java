package pl.speedlog.investments.domain.calculator;

import lombok.EqualsAndHashCode;
import pl.speedlog.investments.domain.Disposal;
import pl.speedlog.investments.domain.Purchase;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
@EqualsAndHashCode
public class WalletRecord implements WalletRecordInfo {

    private final Purchase purchase;
    private final Disposal disposal;

    WalletRecord(Purchase purchase, Disposal disposal) {
        if (!purchase.getNumberOfUnits().equals(disposal.getNumberOfUnits())) {
            throw new IllegalArgumentException("Number of units must be equal");
        }
        this.purchase = purchase;
        this.disposal = disposal;
    }

    @Override
    public boolean isUnsold() {
        return false;
    }

    @Override
    public BigDecimal getUnits() {
        return purchase.getNumberOfUnits();
    }

    @Override
    public LocalDate getPurchaseDate() {
        return purchase.getPurchaseDate();
    }

    @Override
    public BigDecimal getPurchaseValue() {
        return purchase.getValue();
    }

    @Override
    public LocalDate getDisposalDate() {
        return disposal.getDisposalDate();
    }

    @Override
    public BigDecimal getDisposalValue() {
        return disposal.getValue();
    }

    @Override
    public BigDecimal getProfitLoss() {
        return BigDecimalHelper.stripZeros(disposal.getValueIncludeCommision().subtract(purchase.getValue()).subtract(purchase.getCommission()));
    }

    @Override
    public BigDecimal getProfitLossPercentage() {
        BigDecimal purchaseValue = this.purchase.getValueIncludeCommision();
        return BigDecimalHelper.stripZeros(getProfitLoss().divide(purchaseValue, MathContext.DECIMAL128).multiply(BigDecimal.valueOf(100L)));
    }

    @Override
    public BigDecimal getSummaryCommision() {
        return BigDecimalHelper.stripZeros(purchase.getCommission().add(disposal.getCommission()));
    }

    @Override
    public BigDecimal getProfitLossPercentagePerYear() {
        double daysBetweenPurchaseAndDisposal = Long.max(ChronoUnit.DAYS.between(getDisposalDate(), getPurchaseDate()), 1);
        double yearPercentage = 365 / daysBetweenPurchaseAndDisposal;
        return  BigDecimalHelper.stripZeros(getProfitLossPercentage().multiply(BigDecimal.valueOf(yearPercentage)).divide(BigDecimal.valueOf(100L), MathContext.DECIMAL128));
    }
}
