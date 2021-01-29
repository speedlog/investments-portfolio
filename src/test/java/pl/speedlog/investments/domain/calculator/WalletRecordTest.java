package pl.speedlog.investments.domain.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.speedlog.investments.domain.Disposal;
import pl.speedlog.investments.domain.Purchase;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="mailto:mariusz.wyszomierski@coi.gov.pl">mariusz.wyszomierski@coi.gov.pl</a>
 */
class WalletRecordTest {

    @Test
    void unitNumberMustBeTheSame() {
        //given
        Purchase purchase = new Purchase(LocalDate.now(), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO);
        Disposal disposal = new Disposal(LocalDate.now(), BigDecimal.valueOf(9.999), BigDecimal.TEN, BigDecimal.ZERO);

        //when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new WalletRecord(purchase, disposal);
        });

        //then
        assertEquals("Number of units must be equal", exception.getMessage());
    }

    @Test
    void isNotUnsold() {
        //given
        Purchase purchase = new Purchase(LocalDate.now(), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO);
        Disposal disposal = new Disposal(LocalDate.now(), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO);
        WalletRecord walletRecord = new WalletRecord(purchase, disposal);

        //when
        boolean unsold = walletRecord.isUnsold();

        assertFalse(unsold);
    }

    @Test
    void equalsWhenTheSameValues() {
        //given
        Purchase purchase = new Purchase(LocalDate.now(), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO);
        Disposal disposal = new Disposal(LocalDate.now(), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO);
        WalletRecord walletRecord = new WalletRecord(purchase, disposal);
        WalletRecord walletRecord2 = new WalletRecord(purchase, disposal);

        //then
        assertEquals(walletRecord, walletRecord2);
    }

}