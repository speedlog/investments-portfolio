package pl.speedlog.investments.domain.calculator;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
public interface WalletRecordInfo {

    boolean isUnsold();

    BigDecimal getUnits();

    LocalDate getPurchaseDate();

    BigDecimal getPurchaseValue();

    LocalDate getDisposalDate();

    BigDecimal getDisposalValue();

    BigDecimal getProfitLoss();

    BigDecimal getProfitLossPercentage();

    BigDecimal getSummaryCommision();

    BigDecimal getProfitLossPercentagePerYear();
}
