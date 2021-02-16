package pl.speedlog.investments.calculator.controller;

import lombok.RequiredArgsConstructor;
import pl.speedlog.investments.domain.calculator.UnsoldSummary;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
@RequiredArgsConstructor
public class UnsoldSummaryDTO {

    private static final int SCALE = 2;
    private final UnsoldSummary unsoldSummary;

    public BigDecimal getPayment() {
        return unsoldSummary.getPayment()
                .setScale(SCALE, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getAveragePriceUnit() {
        return unsoldSummary.getAveragePriceUnit()
                .setScale(SCALE, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getTodayPrice() {
        return unsoldSummary.getTodayPrice();
    }

    public BigDecimal getUnits() {
        return unsoldSummary.getUnits()
                .setScale(SCALE, RoundingMode.HALF_EVEN);

    }

    public BigDecimal getCommision() {
        return unsoldSummary.getCommision()
                .setScale(SCALE, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getProfitLoss() {
        return unsoldSummary.getProfitLoss()
                .setScale(SCALE, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getProfitLossPercentage() {
        return unsoldSummary.getProfitLossPercentage()
                .setScale(SCALE, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getProfitLossPerYear() {
        return unsoldSummary.getProfitLossPerYear()
                .setScale(SCALE, RoundingMode.HALF_EVEN);
    }

}
