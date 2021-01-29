package pl.speedlog.investments.add.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.speedlog.investments.domain.Disposal;
import pl.speedlog.investments.domain.Purchase;
import pl.speedlog.investments.domain.Wallet;
import pl.speedlog.investments.repository.WalletRepository;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
@RequiredArgsConstructor
@Component
public class AddToWalletUsecase {

    private final WalletRepository walletRepository;

    public void addToWallet(AddToWallet addToWallet) {
        Wallet wallet = walletRepository.findById("CDPROJECT").orElseGet(Wallet::emptyWallet);
        if (Operation.PURCHASE.equals(addToWallet.getOperation())) {
            Purchase purchase = new Purchase(addToWallet.getDate(), addToWallet.getNumberOfUnits(), addToWallet.getPricePerUnit(), addToWallet.getCommision());
            wallet.addPurchase(purchase);
        } else {
            Disposal disposal = new Disposal(addToWallet.getDate(), addToWallet.getNumberOfUnits(), addToWallet.getPricePerUnit(), addToWallet.getCommision());
            wallet.addDisposal(disposal);
        }
        walletRepository.save(wallet);
    }

}
