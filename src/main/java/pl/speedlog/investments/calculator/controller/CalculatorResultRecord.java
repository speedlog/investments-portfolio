package pl.speedlog.investments.calculator.controller;

import lombok.Getter;
import pl.speedlog.investments.domain.calculator.WalletRecordInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
@Getter
class CalculatorResultRecord {

    public static final int SCALE = 2;

    private final WalletRecordInfo walletRecord;

    CalculatorResultRecord(WalletRecordInfo walletRecord) {
        this.walletRecord = walletRecord;
    }

    public boolean isUnsold() {
        return walletRecord.isUnsold();
    }

    public String getUnits() {
        return walletRecord.getUnits()
                .setScale(SCALE, RoundingMode.HALF_EVEN)
                .stripTrailingZeros()
                .toPlainString();
    }

    public LocalDate getPurchaseDate() {
        return walletRecord.getPurchaseDate();
    }

    
    public String getPurchaseValue() {
        return walletRecord.getPurchaseValue()
                .setScale(SCALE, RoundingMode.HALF_EVEN)
                .stripTrailingZeros()
                .toPlainString();
    }

    public LocalDate getDisposalDate() {
        return walletRecord.getDisposalDate();
    }

    public String getDisposalValue() {
        return walletRecord.getDisposalValue()
                .setScale(SCALE, RoundingMode.HALF_EVEN)
                .stripTrailingZeros()
                .toPlainString();
    }

    public String getProfitLoss() {
        return walletRecord.getProfitLoss()
                .setScale(SCALE, RoundingMode.HALF_EVEN)
                .stripTrailingZeros()
                .toPlainString();
    }

    public BigDecimal getProfitLossDecimal() {
        return walletRecord.getProfitLoss();
    }

    public String getProfitLossPercentage() {
        return walletRecord.getProfitLossPercentage()
                .setScale(SCALE, RoundingMode.HALF_EVEN)
                .stripTrailingZeros()
                .toPlainString();
    }

    public String getSummaryCommision() {
        return walletRecord.getSummaryCommision()
                .setScale(SCALE, RoundingMode.HALF_EVEN)
                .stripTrailingZeros()
                .toPlainString();
    }

    public String getProfitLossPercentagePerYear() {
        return walletRecord.getProfitLossPercentagePerYear()
                .setScale(SCALE, RoundingMode.HALF_EVEN)
                .stripTrailingZeros()
                .toPlainString();
    }
}
