package Banks;

import Accounts.ConcreteAccounts.CreditAccount;
import Accounts.ConcreteAccounts.DebitAccount;
import Accounts.ConcreteAccounts.DepositAccount;
import Accounts.Account;
import CentralBank.CentralBank;
import Clients.Client;
import Observing.EventManager;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class Bank {
    @Getter
    private String name;
    @Getter
    private AccountsTerms accountsTerms;
    private final EventManager eventManager;
    private final HashMap<UUID, Account> accounts;

    public Bank(String name, AccountsTerms accountsTerms) {
        this.name = name;
        this.accountsTerms = accountsTerms;
        eventManager = new EventManager();
        accounts = new HashMap<>();
    }

    public void transferMoneyToAccount(Double amount, Account sender, Account receiver) {
        if (receiver.getBank().getName().equals(getName())) {
            receiver.replenishAccount(amount);
        } else {
            CentralBank.getInstance().transferMoneyToAnotherBank(amount, sender, receiver);
        }
    }

    public void calculateInterestForAllAccounts() {
        accounts.values().forEach(Account::calculateInterest);
    }

    public void payInterestForAllAccounts() {
        accounts.values().forEach(Account::payInterest);
    }

    public void deductCommissionForAllAccounts() {
        accounts.values().forEach(Account::deductCommission);
    }

    public void updateAnnualInterest(Double interest) {
        accountsTerms.setAnnualInterest(interest);
        accounts.values().forEach(a -> a.setInterest(interest));
        eventManager.notify("Interest updated");
    }

    public void updateCommission(Double commission) {
        accountsTerms.setCommission(commission);
        accounts.values().forEach(a -> a.setCommission(commission));
        eventManager.notify("Commission updated");
    }

    public void updateCreditLimit(Double creditLimit) {
        accountsTerms.setCreditLimit(creditLimit);
        accounts.values().forEach(account -> {
            if (account instanceof CreditAccount creditAccount) {
                creditAccount.setCreditLimit(creditLimit);
            }
        });
        eventManager.notify("Credit limit updated");
    }

    public Account createDebitAccount(Client client) {
        var accountId = UUID.randomUUID();
        var account = new DebitAccount(
                accountId,
                accountsTerms.getAnnualInterest(),
                client,
                this);
        accounts.put(accountId, account);

        return account;
    }

    public Account createDepositAccount(Double deposit, Client client) {
        var accountId = UUID.randomUUID();
        var account = new DepositAccount(
                accountId,
                deposit,
                accountsTerms.getAnnualInterestForDeposit(deposit),
                LocalDateTime.now().plusMonths(accountsTerms.getDepositAccountTermInMonths()),
                client,
                this);
        accounts.put(accountId, account);

        return account;
    }

    public Account createCreditAccount(Client client) {
        var accountId = UUID.randomUUID();
        var account = new CreditAccount(
                accountId,
                accountsTerms.getCreditLimit(),
                accountsTerms.getCommission(),
                client,
                this);
        accounts.put(accountId, account);

        return account;
    }

    public void deleteAccountById(UUID id) {
        accounts.remove(id);
    }
}