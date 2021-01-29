package pl.speedlog.investments.domain;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
public class Wallet {

    @Id
    private String unitType;

    private final List<Purchase> purchases;
    private final List<Disposal> disposals;

    public static Wallet emptyWallet() {
        return new Wallet(new ArrayList<>(), new ArrayList<>());
    }

    public Wallet(List<Purchase> purchases, List<Disposal> disposals) {
        checkIfDisposalsAreAfterPurchases(purchases, disposals);
        unitType = "CDPROJECT";
        this.purchases = new ArrayList<>(purchases);
        sortPurchases();
        this.disposals = new ArrayList<>(disposals);
        sortDisposals();
    }

    private void sortDisposals() {
        disposals.sort(Comparator.comparing(Disposal::getDisposalDate));
    }

    private void sortPurchases() {
        purchases.sort(Comparator.comparing(Purchase::getPurchaseDate));
    }

    private void checkIfDisposalsAreAfterPurchases(List<Purchase> purchases, List<Disposal> disposals) {
        disposals.forEach(disposal -> {
            BigDecimal purchaseUnits = purchaseUnitsToDate(purchases, disposal.getDisposalDate());
            BigDecimal disposalUnits = disposalUnitsToDate(disposals, disposal.getDisposalDate());
            checkPurchaseUnitsMoreOrEqualDisposalUnits(purchaseUnits, disposalUnits);
        });
    }

    private BigDecimal purchaseUnitsToDate(List<Purchase> purchases, LocalDate date) {
        return purchases.stream()
                .filter(purchase -> purchase.getPurchaseDate().compareTo(date) <= 0)
                .map(Purchase::getNumberOfUnits)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal disposalUnitsToDate(List<Disposal> disposals, LocalDate date) {
        return disposals.stream()
                .filter(disposal -> disposal.getDisposalDate().compareTo(date) <= 0)
                .map(Disposal::getNumberOfUnits)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
        sortPurchases();
    }

    public void addDisposal(Disposal disposal)   {
        List<Disposal> newDisposals = new ArrayList<>(disposals);
        newDisposals.add(disposal);
        checkIfDisposalsAreAfterPurchases(purchases, newDisposals);
        disposals.add(disposal);
        sortDisposals();
    }

    private void checkPurchaseUnitsMoreOrEqualDisposalUnits(BigDecimal purchaseUnits, BigDecimal disposalUnits) {
        if (purchaseUnits.compareTo(disposalUnits) < 0) {
            throw new IllegalStateException("Can't dispose more units then was purchased");
        }
    }

    public void removePurchase(Purchase purchase) {
        List<Purchase> newPurchases = new ArrayList<>(purchases);
        newPurchases.add(purchase);
        checkIfDisposalsAreAfterPurchases(newPurchases, disposals);

        purchases.remove(purchase);
        sortPurchases();
    }

    public void removeDisposal(Disposal disposal) {
        disposals.remove(disposal);
        sortDisposals();
    }

    public List<Purchase> getPurchases() {
        return Collections.unmodifiableList(purchases);
    }

    public List<Disposal> getDisposals() {
        return Collections.unmodifiableList(disposals);
    }
}
