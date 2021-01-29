package pl.speedlog.investments.domain;

import lombok.Getter;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
@Getter
public class DividedPurchase {

    private final Purchase unitsEqualDisposal;
    private final Purchase restUnits;

    DividedPurchase(Purchase unitsEqualDisposal, Purchase restUnits) {
        if (!unitsEqualDisposal.getPurchaseDate().equals(restUnits.getPurchaseDate())) {
            throw new IllegalArgumentException("Purchase dates must be the same");
        }
        if (!unitsEqualDisposal.getUnitPrice().equals(restUnits.getUnitPrice())) {
            throw new IllegalArgumentException("Unit price must be the same");
        }

        this.unitsEqualDisposal = unitsEqualDisposal;
        this.restUnits = restUnits;
    }
}
