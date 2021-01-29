package pl.speedlog.investments.domain.calculator;

import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
@EqualsAndHashCode
class HipoteticalDisposal {

    private final BigDecimal numberOfUnits;
    private final BigDecimal unitPrice;

    HipoteticalDisposal(BigDecimal numberOfUnits, BigDecimal unitPrice) {
        if (numberOfUnits.signum() <= 0) {
            throw new IllegalArgumentException("Number of units must be more than 0");
        }
        if (unitPrice.signum() <= 0) {
            throw new IllegalArgumentException("Unit price must be more than 0");
        }

        this.numberOfUnits = numberOfUnits;
        this.unitPrice = unitPrice;
    }

    public BigDecimal getValue() {
        return  BigDecimalHelper.stripZeros(numberOfUnits.multiply(unitPrice));
    }

    public BigDecimal getNumberOfUnits() {
        return  BigDecimalHelper.stripZeros(numberOfUnits);
    }

    public BigDecimal getUnitPrice() {
        return  BigDecimalHelper.stripZeros(unitPrice);
    }
}
