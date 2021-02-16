package pl.speedlog.investments.domain.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.speedlog.investments.domain.Purchase;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
class UnsoldWalletRecordTest {

    @Test
    void unitNumberMustBeTheSame() {
        //given
        Purchase purchase = new Purchase(LocalDate.now(), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO);
        HipoteticalDisposal hipoteticalDisposal = new HipoteticalDisposal(BigDecimal.valueOf(9.999), BigDecimal.TEN);

        //when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UnsoldWalletRecord(purchase, hipoteticalDisposal);
        });

        //then
        assertEquals("Number of units must be equal", exception.getMessage());
    }

    @Test
    void isUnsold() {
        //given
        Purchase purchase = new Purchase(LocalDate.now(), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO);
        HipoteticalDisposal hipoteticalDisposal = new HipoteticalDisposal(BigDecimal.TEN, BigDecimal.TEN);
        UnsoldWalletRecord unsoldWalletRecord = new UnsoldWalletRecord(purchase, hipoteticalDisposal);

        //when
        boolean unsold = unsoldWalletRecord.isUnsold();

        //then
        assertTrue(unsold);
    }

    @Test
    void disposalDateIsAlwaysNull() {
        //given
        Purchase purchase = new Purchase(LocalDate.now(), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO);
        HipoteticalDisposal hipoteticalDisposal = new HipoteticalDisposal(BigDecimal.TEN, BigDecimal.TEN);
        UnsoldWalletRecord unsoldWalletRecord = new UnsoldWalletRecord(purchase, hipoteticalDisposal);

        //then
        assertNull(unsoldWalletRecord.getDisposalDate());
    }

    @Test
    void equalsWhenTheSameValues() {
        //given
        Purchase purchase = new Purchase(LocalDate.now(), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO);
        HipoteticalDisposal hipoteticalDisposal = new HipoteticalDisposal(BigDecimal.TEN, BigDecimal.TEN);
        UnsoldWalletRecord unsoldWalletRecord = new UnsoldWalletRecord(purchase, hipoteticalDisposal);
        UnsoldWalletRecord unsoldWalletRecord2 = new UnsoldWalletRecord(purchase, hipoteticalDisposal);

        //then
        assertEquals(unsoldWalletRecord, unsoldWalletRecord2);
    }
}