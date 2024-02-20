package Accounts.ConcreteAccounts;

import Accounts.BaseAccount;
import Banks.Bank;
import Clients.Client;
import Exceptions.WithdrawalAmountGreaterThanAccountAmountException;

import java.util.UUID;

public class DebitAccount extends BaseAccount {

    public DebitAccount(UUID id, Double interest, Client owner, Bank bank) {
        super(id, owner, bank);
        this.interest = interest;
    }

    @Override
    public void withdrawMoney(Double amount) {
        if (amount > this.amount) throw new WithdrawalAmountGreaterThanAccountAmountException();
        super.withdrawMoney(amount);
    }
}
