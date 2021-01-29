package pl.speedlog.investments.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.speedlog.investments.domain.Wallet;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
public interface WalletRepository extends MongoRepository<Wallet, String> {

}
