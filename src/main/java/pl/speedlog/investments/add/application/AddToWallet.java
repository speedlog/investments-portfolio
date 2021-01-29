package pl.speedlog.investments.add.application;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
@Getter
@Setter
public class AddToWallet {

    @NotNull
    private Operation operation;

    @PastOrPresent
    private LocalDate date;

    @PositiveOrZero
    private BigDecimal numberOfUnits;

    @PositiveOrZero
    private BigDecimal pricePerUnit;

    private BigDecimal commision;
}
