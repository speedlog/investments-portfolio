package pl.speedlog.investments;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.speedlog.investments.domain.Disposal;
import pl.speedlog.investments.domain.Purchase;
import pl.speedlog.investments.domain.Wallet;
import pl.speedlog.investments.repository.WalletRepository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
@RequiredArgsConstructor
@Component
@Profile("dev")
public class DevData {

    private final WalletRepository walletRepository;

    @PostConstruct
    public void initData() {
        walletRepository.deleteAll();

        List<Purchase> purchases = new ArrayList<>();
        purchases.add(new Purchase(LocalDate.now().minus(150, ChronoUnit.DAYS), BigDecimal.valueOf(50), BigDecimal.valueOf(10L), BigDecimal.ZERO));
        purchases.add(new Purchase(LocalDate.now().minus(90, ChronoUnit.DAYS), BigDecimal.valueOf(100), BigDecimal.valueOf(15L), BigDecimal.valueOf(1L)));
        purchases.add(new Purchase(LocalDate.now().minus(40, ChronoUnit.DAYS), BigDecimal.valueOf(20), BigDecimal.valueOf(40L), BigDecimal.ZERO));
        purchases.add(new Purchase(LocalDate.now().minus(5, ChronoUnit.DAYS), BigDecimal.valueOf(2.6), BigDecimal.valueOf(10L), BigDecimal.ZERO));
        purchases.add(new Purchase(LocalDate.now().minus(5, ChronoUnit.DAYS), BigDecimal.valueOf(2.6), BigDecimal.valueOf(10L), BigDecimal.ZERO));

        List<Disposal> disposals = new ArrayList<>();
        disposals.add(new Disposal(LocalDate.now().minus(100, ChronoUnit.DAYS), BigDecimal.valueOf(25), BigDecimal.valueOf(12.5), BigDecimal.valueOf(0.5)));
        disposals.add(new Disposal(LocalDate.now().minus(50, ChronoUnit.DAYS), BigDecimal.valueOf(10), BigDecimal.valueOf(8.5), BigDecimal.valueOf(1.5)));
        disposals.add(new Disposal(LocalDate.now().minus(40, ChronoUnit.DAYS), BigDecimal.valueOf(20), BigDecimal.valueOf(20L), BigDecimal.valueOf(1.5)));
        disposals.add(new Disposal(LocalDate.now().minus(3, ChronoUnit.DAYS), BigDecimal.valueOf(2.6), BigDecimal.valueOf(15L), BigDecimal.valueOf(0.5)));
        disposals.add(new Disposal(LocalDate.now().minus(3, ChronoUnit.DAYS), BigDecimal.valueOf(2.6), BigDecimal.valueOf(15L), BigDecimal.valueOf(0.5)));

        Wallet wallet = new Wallet(purchases, disposals);
        walletRepository.save(wallet);
    }
}
