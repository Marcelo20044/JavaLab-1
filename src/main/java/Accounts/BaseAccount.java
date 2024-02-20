package Accounts;

import Banks.Bank;
import Clients.Client;
import Exceptions.AmountLessThanZeroException;
import Exceptions.TransactionAmountMoreThanMaxForSuspiciousClientAccountException;
import Transactions.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public abstract class BaseAccount implements Account {
    protected UUID id;
    protected Double amount = 0.0;
    protected Double interest = 0.0;
    protected Double amountWithInterest = 0.0;
    protected Double commission = 0.0;
    protected Client owner;
    protected Bank bank;
    protected Map<UUID, Transaction> transactions;

    public BaseAccount(UUID id, Client owner, Bank bank) {
        this.id = id;
        this.owner = owner;
        this.bank = bank;
        transactions = new HashMap<>();
    }

    /**
     * @exception AmountLessThanZeroException возникает, если попробовать снять со счета отрицательную сумму денег
     *
     * @exception TransactionAmountMoreThanMaxForSuspiciousClientAccountException возникает, если подозрительный
     * аккаунт попробует снять сумму денег, большую максимально доступной суммы для подозрительных аккаунтов
     */
    @Override
    public void withdrawMoney(Double amount) {
        if (amount < 0) throw new AmountLessThanZeroException();
        if (owner.isSuspicious() && amount > bank.getAccountsTerms().getMaxTransactionAmountForSuspiciousClientAccounts())
            throw new TransactionAmountMoreThanMaxForSuspiciousClientAccountException();

        this.amount -= amount;
    }

    /**
     * @exception AmountLessThanZeroException возникает, если попробовать положить на счет отрицательную сумму денег
     */
    @Override
    public void replenishAccount(Double amount) {
        if (amount < 0) throw new AmountLessThanZeroException();
        this.amount += amount;
    }

    /**
     * Метод перевода денег между двумя счетами, для пополнения счета получателся вызывает метод банка
     * @see Bank#transferMoneyToAccount(Double, Account, Account)
     */
    @Override
    public void transferMoney(Account receiver, Double amount) {
        withdrawMoney(amount);
        bank.transferMoneyToAccount(amount, this, receiver);
    }

    @Override
    public void calculateInterest() {
        amountWithInterest += amount * getDailyInterest() / 100;
    }

    @Override
    public void payInterest() {
        amount += amountWithInterest;
    }

    @Override
    public void deductCommission() {
        amount -= commission;
    }

    protected double getDailyInterest() {
        return interest / 365;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactions.put(transaction.getTransactionId(), transaction);
    }

    @Override
    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction.getTransactionId());
    }
}