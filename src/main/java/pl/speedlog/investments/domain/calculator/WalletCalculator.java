package pl.speedlog.investments.domain.calculator;

import lombok.RequiredArgsConstructor;
import pl.speedlog.investments.domain.Disposal;
import pl.speedlog.investments.domain.DividedDisposal;
import pl.speedlog.investments.domain.DividedPurchase;
import pl.speedlog.investments.domain.Purchase;
import pl.speedlog.investments.domain.Wallet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
@RequiredArgsConstructor
public class WalletCalculator {

    private final Wallet wallet;
    private final BigDecimal todayUnitPrice;

    public CalculatorResult calculate() {
        final List<Purchase> purchases = new ArrayList<>(wallet.getPurchases());
        final List<Disposal> disposals = new ArrayList<>(wallet.getDisposals());
        final List<WalletRecord> walletRecords = new ArrayList<>();
        final List<UnsoldWalletRecord> unsoldWalletRecords = new ArrayList<>();

        while (!disposals.isEmpty()) {
            Disposal disposal = findDisposal(disposals);
            Purchase purchase = findPurchase(purchases);
            if (disposal.compareUnits(purchase) == 0) {
                walletRecords.add(new WalletRecord(purchase, disposal));
            }
            if (disposal.compareUnits(purchase) < 0) {
                DividedPurchase dividedPurchase = purchase.divide(disposal.getNumberOfUnits());
                purchases.add(dividedPurchase.getRestUnits());
                walletRecords.add(new WalletRecord(dividedPurchase.getUnitsEqualDisposal(), disposal));
            }
            if (disposal.compareUnits(purchase) > 0) {
                DividedDisposal dividedDisposal = disposal.divide(purchase.getNumberOfUnits());
                disposals.add(dividedDisposal.getRestUnits());
                walletRecords.add(new WalletRecord(purchase, dividedDisposal.getUnitsEqualPurchase()));
            }
            purchases.remove(purchase);
            disposals.remove(disposal);
        }
        if (!purchases.isEmpty()) {
            unsoldWalletRecords.addAll(purchases.stream()
                    .map(purchase -> UnsoldWalletRecord.create(purchase, todayUnitPrice))
                    .collect(Collectors.toList()));
        }
        return new CalculatorResult(walletRecords, unsoldWalletRecords);
    }

    private Purchase findPurchase(List<Purchase> purchases) {
        Optional<Purchase> purchase = purchases.stream().min(Comparator.comparing(Purchase::getPurchaseDate));
        return purchase.orElseThrow();
    }

    private Disposal findDisposal(List<Disposal> disposals) {
        Optional<Disposal> firstDisposal = disposals.stream().min(Comparator.comparing(Disposal::getDisposalDate));
        return firstDisposal.orElseThrow();
    }
}
