package pl.speedlog.investments.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="mailto:mariusz.wyszomierski@coi.gov.pl">mariusz.wyszomierski@coi.gov.pl</a>
 */
class DividedDisposalTest {

    @Test
    void disposalDatesMustBeTheSame() {
        //given
        Disposal disposal1 = new Disposal(LocalDate.now(), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ONE);
        Disposal disposal2 = new Disposal(LocalDate.now().minusDays(1), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ONE);

        //when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new DividedDisposal(disposal1, disposal2);
        });

        //then
        assertEquals("Disposal dates must be the same", exception.getMessage());
    }

    @Test
    void unitNumberMustBeTheSame() {
        //given
        Disposal disposal1 = new Disposal(LocalDate.now(), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ONE);
        Disposal disposal2 = new Disposal(LocalDate.now(), BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ONE);

        //when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new DividedDisposal(disposal1, disposal2);
        });

        //then
        assertEquals("Unit price must be the same", exception.getMessage());
    }
}