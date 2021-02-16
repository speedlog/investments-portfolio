package pl.speedlog.investments.domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pl.speedlog.investments.domain.calculator.CalculatorResult;
import pl.speedlog.investments.domain.calculator.WalletCalculator;
import pl.speedlog.investments.domain.calculator.WalletRecordInfo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author <a href="mailto:mariusz@wyszomierski.pl">mariusz@wyszomierski.pl</a>
 */
public class WalletSteps {

    private CalculatorResult calculatorResult;
    private final List<Purchase> purchases = new ArrayList<>();
    private final List<Disposal> disposals = new ArrayList<>();
    private WalletRecordInfo currentWalletRecord;

    @Given("buy {} units, {} per unit with {} commision")
    public void buy(BigDecimal units, BigDecimal price, BigDecimal comission) {
        purchases.add(new Purchase(LocalDate.now(), units, price, comission));
    }

    @Given("sell {} units, {} per unit with {} commision")
    public void sell(BigDecimal units, BigDecimal price, BigDecimal comission) {
        disposals.add(new Disposal(LocalDate.now(), units, price, comission));
    }

    @When("calculate wallet records")
    public void calculateWallet() {
        calculatorResult = new WalletCalculator(new Wallet(purchases, disposals), BigDecimal.ZERO).calculate();
    }

    @When("calculate wallet records with today price {}")
    public void calculate_wallet_records_with_today_price(BigDecimal todayPrice) {
        calculatorResult = new WalletCalculator(new Wallet(purchases, disposals), todayPrice).calculate();
    }

    @Then("there should not be unsold records")
    public void noUnsoldRecords() {
        assertEquals(0, calculatorResult.getUnsoldWalletRecords().size());
    }

    @Then("there should be {int} wallet record(s)")
    public void there_should_be_wallet_record(Integer count) {
        assertEquals(count, calculatorResult.getWalletRecords().size());
    }

    @Then("there should be {int} unsold record(s)")
    public void there_should_be_unsold_record(Integer count) {
        assertEquals(count, calculatorResult.getUnsoldWalletRecords().size());
    }

    @Then("{word} wallet record with {} unit(s)")
    public void walletRecordUnits(String ordinalNumber, BigDecimal units){
        currentWalletRecord = calculatorResult.getWalletRecords().get(recordNumber(ordinalNumber));
        assertEquals(units, currentWalletRecord.getUnits());
    }

    @Then("{word} unsold wallet record with {} unit(s)")
    public void unsoldWalletRecordUnits(String ordinalNumber, BigDecimal units){
        currentWalletRecord = calculatorResult.getUnsoldWalletRecords().get(recordNumber(ordinalNumber));
        assertEquals(units, currentWalletRecord.getUnits());
    }

    @Then("{} purchase value")
    public void walletRecordPurchaseValue(BigDecimal value){
        assertEquals(value, currentWalletRecord.getPurchaseValue());
    }

    @Then("{} disposal value")
    public void walletRecordDisposalValue(BigDecimal value){
        assertEquals(value, currentWalletRecord.getDisposalValue());
    }

    @Then("{} summary commision")
    public void walletRecordSummaryCommision(BigDecimal summaryCommision){
        assertEquals(summaryCommision, currentWalletRecord.getSummaryCommision());
    }

    @Then("{} \\({}%) profit")
    public void walletRecordProfit(BigDecimal profitValue, BigDecimal profitPercentage){
        assertEquals(profitValue, currentWalletRecord.getProfitLoss());
        assertEquals(profitPercentage, currentWalletRecord.getProfitLossPercentage());
    }

    @Then("{}% profit per year")
    public void walletRecordProfitPerYear(BigDecimal profitPerYear){
        assertEquals(profitPerYear, currentWalletRecord.getProfitLossPercentagePerYear());
    }

    private int recordNumber(String ordinalNumber) {
        if (ordinalNumber.equals("first")) {
            return 0;
        }
        if (ordinalNumber.equals("second")) {
            return 1;
        }
        throw new RuntimeException(ordinalNumber + " is not implemented");
    }
}