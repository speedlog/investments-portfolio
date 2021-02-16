package pl.speedlog.investments.calculator.controller;

import lombok.RequiredArgsConstructor;
import pl.speedlog.investments.domain.calculator.SoldSummary;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
@RequiredArgsConstructor
public class SoldSummaryDTO {

    private static final int SCALE = 2;
    private final SoldSummary soldSummary;

    public BigDecimal getUnits() {
        return soldSummary.getUnits()
                .setScale(SCALE, RoundingMode.HALF_EVEN);

    }

    public BigDecimal getCommision() {
        return soldSummary.getCommision()
                .setScale(SCALE, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getProfitLoss() {
        return soldSummary.getProfitLoss()
                .setScale(SCALE, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getProfitLossPercentage() {
        return soldSummary.getProfitLossPercentage()
                .setScale(SCALE, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getProfitLossPerYear() {
        return soldSummary.getProfitLossPerYear()
                .setScale(SCALE, RoundingMode.HALF_EVEN);
    }

}
