package pl.speedlog.investments.history.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.speedlog.investments.domain.Wallet;
import pl.speedlog.investments.repository.WalletRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
@Controller
@RequiredArgsConstructor
public class WalletHistoryController {

    private final WalletRepository walletRepository;


    @GetMapping("/history")
    public String walletHistory(Model model) {

        Optional<Wallet> walletOptional = walletRepository.findById("CDPROJECT");
        Wallet wallet = walletOptional.orElseGet(Wallet::emptyWallet);

        List<WalletHistoryRecord> historyRecords = new ArrayList<>();

        historyRecords.addAll(wallet.getPurchases().stream()
                .map(purchase -> WalletHistoryRecord.builder()
                        .isPurchase(true)
                        .date(purchase.getPurchaseDate())
                        .numberOfUnits(purchase.getNumberOfUnits())
                        .unitPrice(purchase.getUnitPrice())
                        .commission(purchase.getCommission())
                        .build())
                .collect(Collectors.toList()));

        historyRecords.addAll(wallet.getDisposals().stream()
                .map(disposal -> WalletHistoryRecord.builder()
                        .isPurchase(false)
                        .date(disposal.getDisposalDate())
                        .numberOfUnits(disposal.getNumberOfUnits())
                        .unitPrice(disposal.getUnitPrice())
                        .commission(disposal.getCommission())
                        .build())
                .collect(Collectors.toList()));

        historyRecords.sort(Comparator.comparing(WalletHistoryRecord::getDate));

        model.addAttribute("historyRecords", historyRecords);
        return "history";
    }


}
