package pl.speedlog.investments.domain.calculator;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
@Getter
public class CalculatorResult {

    private final List<WalletRecord> walletRecords;
    private final List<UnsoldWalletRecord> unsoldWalletRecords;

    CalculatorResult(List<WalletRecord> walletRecords, List<UnsoldWalletRecord> unsoldWalletRecords) {
        this.walletRecords = Collections.unmodifiableList(walletRecords);
        this.unsoldWalletRecords = Collections.unmodifiableList(unsoldWalletRecords);
    }
}
