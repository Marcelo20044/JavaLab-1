package Transactions;

import Accounts.Account;

import java.time.LocalDateTime;
import java.util.UUID;

public class WithdrawMoneyTransaction implements Transaction {
    private final UUID id;
    private final Account account;
    private final Double amount;
    private LocalDateTime commitTime;
    public final String transactionType = "Withdrawal";

    public WithdrawMoneyTransaction(UUID id, Account account, Double amount) {
        this.id = id;
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void execute() {
        account.withdrawMoney(amount);
        this.commitTime = LocalDateTime.now();
        account.addTransaction(this);
    }

    @Override
    public void rollback() {
        account.replenishAccount(amount);
        account.removeTransaction(this);
    }

    @Override
    public UUID getTransactionId() {
        return id;
    }
}
