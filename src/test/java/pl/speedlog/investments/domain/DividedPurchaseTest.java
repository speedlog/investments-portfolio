package pl.speedlog.investments.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="mailto:mariusz.wyszomierski@coi.gov.pl">mariusz.wyszomierski@coi.gov.pl</a>
 */
class DividedPurchaseTest {

    @Test
    void purchaseDatesMustBeTheSame() {
        //given
        Purchase purchase1 = new Purchase(LocalDate.now(), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ONE);
        Purchase purchase2 = new Purchase(LocalDate.now().minusDays(1), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ONE);

        //when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new DividedPurchase(purchase1, purchase2);
        });

        //then
        assertEquals("Purchase dates must be the same", exception.getMessage());
    }

    @Test
    void unitNumberMustBeTheSame() {
        //given
        Purchase purchase1 = new Purchase(LocalDate.now(), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ONE);
        Purchase purchase2 = new Purchase(LocalDate.now(), BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ONE);

        //when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new DividedPurchase(purchase1, purchase2);
        });

        //then
        assertEquals("Unit price must be the same", exception.getMessage());
    }

}