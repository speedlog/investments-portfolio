package pl.speedlog.investments.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
class DisposalTest {

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
            new Disposal(invalidDate, validUnitNumber, validUnitPrice, validCommision);
        });

        //then
        assertEquals("Disposal date can't be from future", exception.getMessage());
    }

    @Test
    void disposalNumberOfUnitsMustBeMoreThen0() {
        //given
        BigDecimal invalidNumberUnits = BigDecimal.ZERO;

        //when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Disposal(validDate, invalidNumberUnits, validUnitPrice, validCommision);
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
            new Disposal(validDate, validUnitNumber, invalidUnitPrice, validCommision);
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
            new Disposal(validDate, validUnitNumber, validUnitPrice, invalidCommision);
        });

        //then
        assertEquals("Commission must be more or equal 0", exception.getMessage());
    }

    @Test
    void unitNumberMustMoreThan0() {
        // given
        Disposal disposal = new Disposal(LocalDate.now(), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO);

        //when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            disposal.divide(BigDecimal.ZERO);
        });

        //then
        assertEquals("Purchase units must be more than 0", exception.getMessage());
    }

    @Test
    void unitNumberMustLowerThanInDisposal() {
        // given
        BigDecimal theSameUnitsNumber = BigDecimal.TEN;
        Disposal disposal = new Disposal(LocalDate.now(), theSameUnitsNumber, BigDecimal.TEN, BigDecimal.ZERO);

        //when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            disposal.divide(theSameUnitsNumber);
        });

        //then
        assertEquals("Purchase units must be lower than disposal units", exception.getMessage());
    }


}