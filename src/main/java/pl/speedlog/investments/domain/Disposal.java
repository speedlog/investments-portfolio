package pl.speedlog.investments.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.speedlog.investments.domain.calculator.BigDecimalHelper;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Clock;
import java.time.LocalDate;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
@EqualsAndHashCode
@Getter
public class Disposal {

    private final LocalDate disposalDate;
    private final BigDecimal numberOfUnits;
    private final BigDecimal unitPrice;
    private final BigDecimal commission;

    public Disposal(LocalDate disposalDate, BigDecimal numberOfUnits, BigDecimal unitPrice, BigDecimal commission) {
        if (disposalDate.isAfter(LocalDate.now(Clock.systemDefaultZone()))) {
            throw new IllegalArgumentException("Disposal date can't be from future");
        }
        if (numberOfUnits.signum() <= 0) {
            throw new IllegalArgumentException("Number of units must be more than 0");
        }
        if (unitPrice.signum() <= 0) {
            throw new IllegalArgumentException("Unit price must be more than 0");
        }
        if (commission != null && commission.signum() < 0) {
            throw new IllegalArgumentException("Commission must be more or equal 0");
        }
        this.disposalDate = disposalDate;
        this.numberOfUnits = numberOfUnits;
        this.unitPrice = unitPrice;
        this.commission = commission;
    }

    public BigDecimal getValue() {
        return BigDecimalHelper.stripZeros(numberOfUnits.multiply(unitPrice));
    }

    public int compareUnits(Purchase purchase) {
        return this.numberOfUnits.compareTo(purchase.getNumberOfUnits());
    }

    public BigDecimal getValueIncludeCommision() {
        return BigDecimalHelper.stripZeros(numberOfUnits.multiply(unitPrice).subtract(commission));
    }

    public BigDecimal getNumberOfUnits() {
        return  BigDecimalHelper.stripZeros(numberOfUnits);
    }

    public BigDecimal getUnitPrice() {
        return  BigDecimalHelper.stripZeros(unitPrice);
    }

    public BigDecimal getCommission() {
        return  BigDecimalHelper.stripZeros(commission);
    }

    public DividedDisposal divide(BigDecimal purchaseUnitsNumber) {
        if (purchaseUnitsNumber.signum() <= 0) {
            throw new IllegalArgumentException("Purchase units must be more than 0");
        }
        if (purchaseUnitsNumber.compareTo(numberOfUnits) >= 0) {
            throw new IllegalArgumentException("Purchase units must be lower than disposal units");
        }

        BigDecimal unitDifference = numberOfUnits.subtract(purchaseUnitsNumber);
        BigDecimal purchasePrecentage = BigDecimal.ONE.subtract(unitDifference.divide(numberOfUnits, MathContext.DECIMAL128));

        Disposal unitsEqualPurchase = new Disposal(disposalDate, purchaseUnitsNumber, unitPrice, purchasePrecentage.multiply(commission));
        Disposal restUnits = new Disposal(disposalDate, unitDifference, unitPrice, BigDecimal.ONE.subtract(purchasePrecentage).multiply(commission));
        return new DividedDisposal(unitsEqualPurchase, restUnits);
    }

}
