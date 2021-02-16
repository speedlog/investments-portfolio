package pl.speedlog.investments.domain.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
class HipoteticalDisposalTest {

    @Test
    void numberOfUnitsMustBeGreaterThan0() {
        //when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new HipoteticalDisposal(BigDecimal.ZERO, BigDecimal.TEN);
        });

        //then
        assertEquals("Number of units must be more than 0", exception.getMessage());
    }

    @Test
    void unitPriceMustBeGreaterThan0() {
        //when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new HipoteticalDisposal(BigDecimal.TEN, BigDecimal.ZERO);
        });

        //then
        assertEquals("Unit price must be more than 0", exception.getMessage());
    }

    @Test
    void equalsWhenTheSameValues() {
        //given
        HipoteticalDisposal hipoteticalDisposal = new HipoteticalDisposal(BigDecimal.TEN, BigDecimal.TEN);
        HipoteticalDisposal hipoteticalDisposal2 = new HipoteticalDisposal(BigDecimal.TEN, BigDecimal.TEN);

        //then
        assertEquals(hipoteticalDisposal, hipoteticalDisposal2);
    }

}