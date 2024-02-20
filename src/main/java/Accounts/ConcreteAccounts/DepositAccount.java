package Accounts.ConcreteAccounts;

import Accounts.BaseAccount;
import Accounts.Account;
import Banks.Bank;
import Clients.Client;
import Exceptions.InvalidOperationWithOpenDepositAccountException;
import Exceptions.WithdrawalAmountGreaterThanAccountAmountException;

import java.time.LocalDateTime;
import java.util.UUID;

public class DepositAccount extends BaseAccount {
    private final LocalDateTime accountTerm;

    public DepositAccount(
            UUID id,
            double deposit,
            double interest,
            LocalDateTime accountTerm,
            Client owner,
            Bank bank) {
        super(id, owner, bank);
        this.amount = deposit;
        this.interest = interest;
        this.accountTerm = accountTerm;
    }

    @Override
    public void withdrawMoney(Double amount) {
        if (amount > this.amount) throw new WithdrawalAmountGreaterThanAccountAmountException();
        if (LocalDateTime.now().isBefore(accountTerm))
            throw new InvalidOperationWithOpenDepositAccountException("operation: withdrawal");
        super.withdrawMoney(amount);
    }

    @Override
    public void transferMoney(Account receiver, Double amount) {
        if (LocalDateTime.now().isBefore(accountTerm))
            throw new InvalidOperationWithOpenDepositAccountException("operation: transfer");
        super.transferMoney(receiver, amount);
    }
}