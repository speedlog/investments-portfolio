package pl.speedlog.investments.domain;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author <a href="mailto:mariusz.wyszomierski@coi.gov.pl">mariusz.wyszomierski@coi.gov.pl</a>
 */
class WalletTest {

    @Test
    void createEmptyWallet() {
        //when
        Wallet wallet = Wallet.emptyWallet();

        //then
        assertEquals(0, wallet.getPurchases().size());
        assertEquals(0, wallet.getDisposals().size());
    }

    @Test
    void okTwoPurchasesAndDisposalsTheSameDateAndUnits() {
        //given
        List<Purchase> purchases = Lists.list(
                new Purchase(LocalDate.of(2020, 1, 1), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO),
                new Purchase(LocalDate.of(2020, 1, 1), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO)
        );


        List<Disposal> disposals = Lists.list(
                new Disposal(LocalDate.of(2020, 1, 1), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO),
                new Disposal(LocalDate.of(2020, 1, 1), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO)
        );

        //then
        Assertions.assertDoesNotThrow(() -> new Wallet(purchases, disposals));
    }

    @Test
    void okOneMorePurchaseThenDisposal() {
        //given
        List<Purchase> purchases = Lists.list(
                new Purchase(LocalDate.of(2020, 1, 1), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO),
                new Purchase(LocalDate.of(2020, 1, 1), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO)
        );


        List<Disposal> disposals = Lists.list(
                new Disposal(LocalDate.of(2020, 1, 1), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO)
        );

        //then
        Assertions.assertDoesNotThrow(() -> new Wallet(purchases, disposals));
    }

    @Test
    void okOneMoreDispsalThenPurchase() {
        //given
        List<Purchase> purchases = Lists.list(
                new Purchase(LocalDate.of(2020, 1, 1), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO)
        );


        List<Disposal> disposals = Lists.list(
                new Disposal(LocalDate.of(2020, 1, 1), BigDecimal.valueOf(5), BigDecimal.TEN, BigDecimal.ZERO),
                new Disposal(LocalDate.of(2020, 1, 1), BigDecimal.valueOf(5), BigDecimal.TEN, BigDecimal.ZERO)
        );

        //then
        Assertions.assertDoesNotThrow(() -> new Wallet(purchases, disposals));
    }

    @Test
    void errorDisposalWithoutPurchase() {
        //given
        List<Purchase> purchases = new ArrayList<>();


        List<Disposal> disposals = Lists.list(
                new Disposal(LocalDate.of(2020, 1, 1), BigDecimal.valueOf(5), BigDecimal.TEN, BigDecimal.ZERO)
        );

        //then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            new Wallet(purchases, disposals);
        });
    }

    @Test
    void errorDisposalMoreUnitsThenInPurchase() {
        //given
        List<Purchase> purchases = Lists.list(
                new Purchase(LocalDate.of(2020, 1, 1), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO)
        );


        List<Disposal> disposals = Lists.list(
                new Disposal(LocalDate.of(2020, 1, 1), BigDecimal.valueOf(11.0001), BigDecimal.TEN, BigDecimal.ZERO)
        );

        //then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            new Wallet(purchases, disposals);
        });
    }

    @Test
    void errorDisposalMoreUnitsThenInPurchase2() {
        //given
        List<Purchase> purchases = Lists.list(
                new Purchase(LocalDate.of(2020, 1, 1), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO),
                new Purchase(LocalDate.of(2020, 1, 2), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO),
                new Purchase(LocalDate.of(2020, 1, 3), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO),
                new Purchase(LocalDate.of(2020, 1, 4), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO)
        );


        List<Disposal> disposals = Lists.list(
                new Disposal(LocalDate.of(2020, 1, 2), BigDecimal.valueOf(20), BigDecimal.TEN, BigDecimal.ZERO),
                new Disposal(LocalDate.of(2020, 1, 5), BigDecimal.valueOf(21), BigDecimal.TEN, BigDecimal.ZERO)
        );

        //then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            new Wallet(purchases, disposals);
        });
    }

    @Test
    void errorDisposalMoreUnitsThenInPurchase3() {
        //given
        List<Purchase> purchases = Lists.list(
                new Purchase(LocalDate.of(2020, 1, 1), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO),
                new Purchase(LocalDate.of(2020, 1, 4), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO)
        );


        List<Disposal> disposals = Lists.list(
                new Disposal(LocalDate.of(2020, 1, 2), BigDecimal.valueOf(5), BigDecimal.TEN, BigDecimal.ZERO),
                new Disposal(LocalDate.of(2020, 1, 3), BigDecimal.valueOf(5), BigDecimal.TEN, BigDecimal.ZERO),
                new Disposal(LocalDate.of(2020, 1, 4), BigDecimal.valueOf(5), BigDecimal.TEN, BigDecimal.ZERO),
                new Disposal(LocalDate.of(2020, 1, 5), BigDecimal.valueOf(6), BigDecimal.TEN, BigDecimal.ZERO)
        );

        //then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            new Wallet(purchases, disposals);
        });
    }


    @Test
    void errorDisposalDateBeforePurchase() {
        //given
        List<Purchase> purchases = Lists.list(
                new Purchase(LocalDate.of(2020, 1, 2), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO)
        );


        List<Disposal> disposals = Lists.list(
                new Disposal(LocalDate.of(2020, 1, 1), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO)
        );

        //then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            new Wallet(purchases, disposals);
        });
    }

    @Test
    void errorDisposalDateBeforePurchase2() {
        //given
        List<Purchase> purchases = Lists.list(
                new Purchase(LocalDate.of(2020, 1, 1), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO),
                new Purchase(LocalDate.of(2020, 1, 5), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO)
        );


        List<Disposal> disposals = Lists.list(
                new Disposal(LocalDate.of(2020, 1, 1), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO),
                new Disposal(LocalDate.of(2020, 1, 4), BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ZERO)
        );

        //then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            new Wallet(purchases, disposals);
        });
    }

    @Test
    void removePurchaseTest() {
        //given
        Wallet wallet = new Wallet(new ArrayList<>(), new ArrayList<>());
        Purchase purchase = new Purchase(LocalDate.of(2020, 1, 1), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO);
        wallet.addPurchase(purchase);
        wallet.addPurchase(purchase);

        //when
        wallet.removePurchase(purchase);

        //then
        assertEquals(1, wallet.getPurchases().size());
        assertEquals(purchase, wallet.getPurchases().get(0));
    }

    @Test
    void removeDisposalTest() {
        //given
        Wallet wallet = new Wallet(new ArrayList<>(), new ArrayList<>());
        Purchase purchase = new Purchase(LocalDate.of(2020, 1, 1), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO);
        wallet.addPurchase(purchase);
        wallet.addPurchase(purchase);
        Disposal disposal = new Disposal(LocalDate.of(2020, 1, 1), BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO);
        wallet.addDisposal(disposal);
        wallet.addDisposal(disposal);

        //when
        wallet.removeDisposal(disposal);

        //then
        assertEquals(2, wallet.getPurchases().size());
        assertEquals(1, wallet.getDisposals().size());
        assertEquals(disposal, wallet.getDisposals().get(0));
    }

}