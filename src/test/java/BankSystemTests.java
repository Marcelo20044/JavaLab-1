import Accounts.Account;
import Banks.AccountsTerms;
import Banks.Bank;
import CentralBank.CentralBank;
import Clients.Builders.ClientBuilder;
import Clients.Client;
import Exceptions.OutOfCreditLimitException;
import Transactions.ReplenishAccountTransaction;
import Transactions.TransferMoneyTransaction;
import Transactions.WithdrawMoneyTransaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.TreeMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class BankSystemTests {

    TreeMap<Double, Double> annualInterestsForDeposits;
    CentralBank centralBank;
    Bank sberbank;
    Bank tinkoff;
    Client clientLixaLivushkin;
    Client clientSaksIgnatov;
    Account lixaDebitAccount;
    Account saksCreditAccount;

    public BankSystemTests() {
        centralBank = CentralBank.getInstance();

        annualInterestsForDeposits = new TreeMap<>();
        annualInterestsForDeposits.put(0.0, 3.0);
        annualInterestsForDeposits.put(50000.0, 3.5);
        annualInterestsForDeposits.put(100000.0, 4.0);
        AccountsTerms accountsTerms = new AccountsTerms(
                10.0, 100.0, 50000.0, 3,
                50000.0, annualInterestsForDeposits);

        sberbank = centralBank.createBank("Sberbank", accountsTerms);
        tinkoff = centralBank.createBank("Tinkoff", accountsTerms);

    }

    @BeforeEach
    public void setUp() {
        clientLixaLivushkin = new ClientBuilder().withName("Lixa").withSurname("Livushkin")
                .withAddress("Elbrus 52").witPassport(11, 1111).build();
        lixaDebitAccount = sberbank.createDebitAccount(clientLixaLivushkin);

        clientSaksIgnatov = new ClientBuilder().withName("Saks").withSurname("Ignatov")
                .withAddress("Chkalovskii 54").witPassport(12, 3456).build();
        saksCreditAccount = sberbank.createCreditAccount(clientSaksIgnatov);
    }

    @Test
    public void successAccountReplenishment() {
        var replenishment = new ReplenishAccountTransaction(UUID.randomUUID(), lixaDebitAccount, 500.0);
        replenishment.execute();

        var expected = 500.0;
        var actual = lixaDebitAccount.getAmount();
        assertEquals(expected, actual);
    }

    @Test
    public void successMoneyWithdrawal() {
        var replenishment = new ReplenishAccountTransaction(UUID.randomUUID(), lixaDebitAccount, 500.0);
        replenishment.execute();
        var withdrawal = new WithdrawMoneyTransaction(UUID.randomUUID(), lixaDebitAccount, 300.0);
        withdrawal.execute();

        var expected = 200.0;
        var actual = lixaDebitAccount.getAmount();;
        assertEquals(expected, actual);
    }

    @Test
    public void successMoneyTransfer() {
        var replenishment = new ReplenishAccountTransaction(UUID.randomUUID(), lixaDebitAccount, 500.0);
        replenishment.execute();
        var transfer = new TransferMoneyTransaction(UUID.randomUUID(), lixaDebitAccount, saksCreditAccount, 250.0);
        transfer.execute();

        var expected = 250.0;
        var actual1 = lixaDebitAccount.getAmount();
        var actual2 = saksCreditAccount.getAmount();

        assertEquals(expected, actual1);
        assertEquals(expected, actual2);
    }

    @Test
    public void invalidMoneyWithdrawalFromCreditAccountWithAmountMoreThanLimit() {
        var replenishment = new ReplenishAccountTransaction(UUID.randomUUID(), saksCreditAccount, 500.0);
        replenishment.execute();
        var withdrawal = new WithdrawMoneyTransaction(UUID.randomUUID(), saksCreditAccount, 60000.0);

        var actual = new Exception();
        try {
            withdrawal.execute();
        } catch (Exception e) {
            actual = e;
        }

        assertInstanceOf(OutOfCreditLimitException.class, actual);
    }

    @Test
    public void successCommissionDeduction() {
        var replenishment = new ReplenishAccountTransaction(UUID.randomUUID(), saksCreditAccount, 500.0);
        replenishment.execute();
        var centralBank = CentralBank.getInstance();
        centralBank.timeMachine(30);

        var expected = 400.0;
        var actual = saksCreditAccount.getAmount();
        assertEquals(expected, actual);
    }

    @Test
    public void successTransferToAnotherBankWithCommission() {
        var saksDebitTinkoffAccount = tinkoff.createDebitAccount(clientSaksIgnatov);
        var replenishment = new ReplenishAccountTransaction(UUID.randomUUID(), lixaDebitAccount, 500.0);
        replenishment.execute();
        var transfer = new TransferMoneyTransaction(UUID.randomUUID(), lixaDebitAccount, saksDebitTinkoffAccount, 250.0);
        transfer.execute();

        var expected1 = 225.0;
        var actual1 = lixaDebitAccount.getAmount();
        var expected2 = 250;
        var actual2 = saksDebitTinkoffAccount.getAmount();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }
}
