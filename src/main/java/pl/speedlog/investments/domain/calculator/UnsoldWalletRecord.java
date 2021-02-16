package pl.speedlog.investments.domain.calculator;

import lombok.EqualsAndHashCode;
import pl.speedlog.investments.domain.Purchase;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
@EqualsAndHashCode
public class UnsoldWalletRecord implements WalletRecordInfo {

    private final Purchase purchase;
    private final HipoteticalDisposal hipoteticalDisposal;

    UnsoldWalletRecord(Purchase purchase, HipoteticalDisposal hipoteticalDisposal) {
        if (!purchase.getNumberOfUnits().equals(hipoteticalDisposal.getNumberOfUnits())) {
            throw new IllegalArgumentException("Number of units must be equal");
        }

        this.purchase = purchase;
        this.hipoteticalDisposal = hipoteticalDisposal;
    }

    static UnsoldWalletRecord create(Purchase purchase, BigDecimal todayUnitPrice) {
        HipoteticalDisposal hipoteticalDisposal = new HipoteticalDisposal(purchase.getNumberOfUnits(), todayUnitPrice);
        return new UnsoldWalletRecord(purchase, hipoteticalDisposal);
    }

    @Override
    public boolean isUnsold() {
        return true;
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
        return null;
    }

    @Override
    public BigDecimal getDisposalValue() {
        return hipoteticalDisposal.getValue();
    }

    @Override
    public BigDecimal getProfitLoss() {
        return  BigDecimalHelper.stripZeros(hipoteticalDisposal.getValue().subtract(purchase.getValueIncludeCommision()));
    }

    @Override
    public BigDecimal getProfitLossPercentage() {
        BigDecimal purchaseValue = this.purchase.getValueIncludeCommision();
        return BigDecimalHelper.stripZeros(getProfitLoss().divide(purchaseValue, MathContext.DECIMAL128).multiply(BigDecimal.valueOf(100L)));
    }

    @Override
    public BigDecimal getSummaryCommision() {
        return purchase.getCommission();
    }

    @Override
    public BigDecimal getProfitLossPercentagePerYear() {
        double daysBetweenPurchaseAndDisposal = Long.max(ChronoUnit.DAYS.between(LocalDate.now(), getPurchaseDate()), 1);
        double yearPercentage = 365 / daysBetweenPurchaseAndDisposal;
        return BigDecimalHelper.stripZeros(getProfitLossPercentage().multiply(BigDecimal.valueOf(yearPercentage)).divide(BigDecimal.valueOf(100L), MathContext.DECIMAL128));
    }

    public BigDecimal getPurchaseUnitPrice() {
        return purchase.getUnitPrice();
    }

    public BigDecimal getDisposalUnitPrice() {
        return hipoteticalDisposal.getUnitPrice();
    }
}
