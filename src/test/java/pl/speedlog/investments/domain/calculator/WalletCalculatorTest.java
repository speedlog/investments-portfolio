package pl.speedlog.investments.domain.calculator;

import org.junit.jupiter.api.Test;
import pl.speedlog.investments.domain.Disposal;
import pl.speedlog.investments.domain.Purchase;
import pl.speedlog.investments.domain.Wallet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
class WalletCalculatorTest {

    private Purchase purchase1 = new Purchase(LocalDate.now().minusDays(10), BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ONE);
    private Purchase purchase2 = new Purchase(LocalDate.now().minusDays(5), BigDecimal.TEN, BigDecimal.valueOf(20L), BigDecimal.valueOf(2));

    private Disposal disposal1 = new Disposal(LocalDate.now().minusDays(6), BigDecimal.ONE, BigDecimal.valueOf(0.9), BigDecimal.ZERO);
    private Disposal disposal2 = new Disposal(LocalDate.now().minusDays(4), BigDecimal.TEN, BigDecimal.valueOf(22L), BigDecimal.ONE);
    private Disposal disposal3 = new Disposal(LocalDate.now().minusDays(1), BigDecimal.ONE, BigDecimal.valueOf(25L), BigDecimal.ONE);
    private List<Purchase> purchases = List.of(purchase1, purchase2);
    private List<Disposal> disposals = List.of(disposal1, disposal2, disposal3);

    @Test
    void shouldCalculateSoldSummary() {
        //given
        Wallet wallet = new Wallet(purchases, disposals);
        WalletCalculator walletCalculator = new WalletCalculator(wallet, BigDecimal.valueOf(30));

        //when
        CalculatorResult result = walletCalculator.calculate();

        //then
        SoldSummary soldSummary = result.getSoldSummary();
        assertEquals(BigDecimal.valueOf(12), soldSummary.getUnits());
        assertEquals(BigDecimal.valueOf(3.4000000), soldSummary.getCommision().setScale(1, RoundingMode.HALF_EVEN));
        assertEquals(BigDecimal.valueOf(192.5), soldSummary.getProfitLoss().setScale(1, RoundingMode.HALF_EVEN));
        assertEquals(BigDecimal.valueOf(579.1666667), soldSummary.getProfitLossPercentage().setScale(7, RoundingMode.HALF_EVEN));
        assertEquals(BigDecimal.valueOf(2113.9583333), soldSummary.getProfitLossPerYear().setScale(7, RoundingMode.HALF_EVEN));
    }

    @Test
    void shouldCalculateUnsoldSummary() {
        //given
        Wallet wallet = new Wallet(purchases, disposals);
        WalletCalculator walletCalculator = new WalletCalculator(wallet, BigDecimal.valueOf(30));

        //when
        CalculatorResult result = walletCalculator.calculate();

        //then
        UnsoldSummary unsoldSummary = result.getUnsoldSummary();
        assertEquals(BigDecimal.valueOf(8), unsoldSummary.getUnits());
        assertEquals(BigDecimal.valueOf(160), unsoldSummary.getPayment());
        assertEquals(BigDecimal.valueOf(20), unsoldSummary.getAveragePriceUnit());
        assertEquals(BigDecimal.valueOf(1.6), unsoldSummary.getCommision().setScale(1, RoundingMode.HALF_EVEN));
        assertEquals(BigDecimal.valueOf(30), unsoldSummary.getTodayPrice());
        assertEquals(BigDecimal.valueOf(81.6), unsoldSummary.getProfitLoss().setScale(1, RoundingMode.HALF_EVEN));
        assertEquals(BigDecimal.valueOf(51.5151515), unsoldSummary.getProfitLossPercentage().setScale(7, RoundingMode.HALF_EVEN));
        assertEquals(BigDecimal.valueOf(188.030303), unsoldSummary.getProfitLossPerYear().setScale(6, RoundingMode.HALF_EVEN));
    }
}