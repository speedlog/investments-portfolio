package pl.speedlog.investments.calculator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.speedlog.investments.domain.Wallet;
import pl.speedlog.investments.domain.calculator.CalculatorResult;
import pl.speedlog.investments.domain.calculator.WalletCalculator;
import pl.speedlog.investments.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
@Controller
@RequiredArgsConstructor
public class WalletCalculatorController {

    private final WalletRepository walletRepository;


    @GetMapping("/calculator")
    public String walletHistory(Model model) {

        Wallet wallet = walletRepository.findById("CDPROJECT").orElseGet(Wallet::emptyWallet);

        WalletCalculator calculator = new WalletCalculator(wallet, BigDecimal.valueOf(10L));
        CalculatorResult calculatorResult = calculator.calculate();

        List<CalculatorResultRecord> records = new ArrayList<>();
        calculatorResult.getWalletRecords().forEach(
                walletRecord -> records.add(new CalculatorResultRecord(walletRecord))
        );
        calculatorResult.getUnsoldWalletRecords().forEach(
            unsoldWalletRecord -> records.add(new CalculatorResultRecord(unsoldWalletRecord))
        );

        records.sort(Comparator.comparing(CalculatorResultRecord::getPurchaseDate));

        model.addAttribute("records", records);
        return "calculator";
    }


}
