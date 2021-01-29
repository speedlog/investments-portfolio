package pl.speedlog.investments.domain;

import lombok.Getter;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
@Getter
public class DividedDisposal {

    private final Disposal unitsEqualPurchase;
    private final Disposal restUnits;

    DividedDisposal(Disposal unitsEqualPurchase, Disposal restUnits) {
        if (!unitsEqualPurchase.getDisposalDate().equals(restUnits.getDisposalDate())) {
            throw new IllegalArgumentException("Disposal dates must be the same");
        }
        if (!unitsEqualPurchase.getUnitPrice().equals(restUnits.getUnitPrice())) {
            throw new IllegalArgumentException("Unit price must be the same");
        }

        this.unitsEqualPurchase = unitsEqualPurchase;
        this.restUnits = restUnits;
    }
}
