package Transactions;

import Accounts.Account;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransferMoneyTransaction implements Transaction {
    private final UUID id;
    private final Account account;
    private final Account receiver;
    private final Double amount;
    private LocalDateTime commitTime;
    public final String transactionType = "Transfer";

    public TransferMoneyTransaction(UUID id, Account account, Account receiver, Double amount) {
        this.id = id;
        this.account = account;
        this.receiver = receiver;
        this.amount = amount;
    }

    @Override
    public void execute() {
        account.transferMoney(receiver, amount);
        this.commitTime = LocalDateTime.now();
        account.addTransaction(this);
    }

    @Override
    public void rollback() {
        receiver.transferMoney(account, amount);
        account.removeTransaction(this);
    }

    @Override
    public UUID getTransactionId() {
        return id;
    }
}
