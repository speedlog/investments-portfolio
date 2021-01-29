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
public class Purchase {

    private final LocalDate purchaseDate;
    private final BigDecimal numberOfUnits;
    private final BigDecimal unitPrice;
    private final BigDecimal commission;

    public Purchase(LocalDate purchaseDate, BigDecimal numberOfUnits, BigDecimal unitPrice, BigDecimal commission) {
        if (purchaseDate.isAfter(LocalDate.now(Clock.systemDefaultZone()))) {
            throw new IllegalArgumentException("Purchase date can't be from future");
        }
        if (numberOfUnits.signum() <= 0) {
            throw new IllegalArgumentException("Number of units must be more than 0");
        }
        if (unitPrice.signum() <= 0) {
            throw new IllegalArgumentException("Unit price must be more than 0");
        }
        if (commission != null && commission.signum() < 0) {
            throw new IllegalArgumentException("Commission must be more or equal 0");
        }        this.purchaseDate = purchaseDate;
        this.numberOfUnits = numberOfUnits;
        this.unitPrice = unitPrice;
        this.commission = commission;
    }

    public BigDecimal getValue() {
        return BigDecimalHelper.stripZeros(numberOfUnits.multiply(unitPrice));
    }

    public BigDecimal getValueIncludeCommision() {
        return BigDecimalHelper.stripZeros(numberOfUnits.multiply(unitPrice).subtract(commission));
    }

    public BigDecimal getNumberOfUnits() {
        return BigDecimalHelper.stripZeros(numberOfUnits);
    }

    public BigDecimal getUnitPrice() {
        return BigDecimalHelper.stripZeros(unitPrice);
    }

    public BigDecimal getCommission() {
        return BigDecimalHelper.stripZeros(commission);
    }

    public DividedPurchase divide(BigDecimal disposalUnitsNumber) {
        if (disposalUnitsNumber.signum() <= 0) {
            throw new IllegalArgumentException("Disposal units must be more than 0");
        }
        if (disposalUnitsNumber.compareTo(numberOfUnits) >= 0) {
            throw new IllegalArgumentException("Disposal units must be lower than purchase units");
        }

        BigDecimal unitDifference = numberOfUnits.subtract(disposalUnitsNumber);
        BigDecimal disposalPrecentage = BigDecimal.ONE.subtract(unitDifference.divide(numberOfUnits, MathContext.DECIMAL128));

        Purchase unitsEqualDisposal = new Purchase(purchaseDate, disposalUnitsNumber, unitPrice, disposalPrecentage.multiply(commission));
        Purchase restUnits = new Purchase(purchaseDate, unitDifference, unitPrice, BigDecimal.ONE.subtract(disposalPrecentage).multiply(commission));
        return new DividedPurchase(unitsEqualDisposal, restUnits);
    }
}
