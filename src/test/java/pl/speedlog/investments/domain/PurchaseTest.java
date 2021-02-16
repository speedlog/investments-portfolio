package pl.speedlog.investments.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
class PurchaseTest {

    private final LocalDate validDate = LocalDate.now();
    private final BigDecimal validUnitNumber = BigDecimal.valueOf(0.1);
    private final BigDecimal validUnitPrice = BigDecimal.valueOf(0.1);
    private final BigDecimal validCommision = BigDecimal.ZERO;


    @Test
    void disposalDateCantBeFromFuture() {
        //given
        LocalDate invalidDate = LocalDate.now().plus(1, ChronoUnit.DAYS);

        //when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Purchase(invalidDate, validUnitNumber, validUnitPrice, validCommision);
        });

        //then
        assertEquals("Purchase date can't be from future", exception.getMessage());
    }

    @Test
    void disposalNumberOfUnitsMustBeMoreThen0() {
        //given
        BigDecimal invalidNumberUnits = BigDecimal.ZERO;

        //when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Purchase(validDate, invalidNumberUnits, validUnitPrice, validCommision);
        });

        //then
        assertEquals("Number of units must be more than 0", exception.getMessage());
    }

    @Test
    void valueMustBeMoreThen0() {
        //given
        BigDecimal invalidUnitPrice = BigDecimal.ZERO;

        //when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Purchase(validDate, validUnitNumber, invalidUnitPrice, validCommision);
        });

        //then
        assertEquals("Unit price must be more than 0", exception.getMessage());
    }

    @Test
    void commisionMustBeMoreOrEqual0() {
        //given
        BigDecimal invalidCommision = BigDecimal.valueOf(-0.1);

        //when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Purchase(validDate, validUnitNumber, validUnitPrice, invalidCommision);
        });

        //then
        assertEquals("Commission must be more or equal 0", exception.getMessage());
    }

    @Test
    void unitNumberMustMoreThan0() {
        // given
        Purchase purchase = new Purchase(LocalDate.now(), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO);

        //when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            purchase.divide(BigDecimal.ZERO);
        });

        //then
        assertEquals("Disposal units must be more than 0", exception.getMessage());
    }

    @Test
    void unitNumberMustLowerThanInPurchase() {
        // given
        BigDecimal theSameUnitsNumber = BigDecimal.TEN;
        Purchase purchase = new Purchase(LocalDate.now(), theSameUnitsNumber, BigDecimal.TEN, BigDecimal.ZERO);

        //when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            purchase.divide(theSameUnitsNumber);
        });

        //then
        assertEquals("Disposal units must be lower than purchase units", exception.getMessage());
    }
}