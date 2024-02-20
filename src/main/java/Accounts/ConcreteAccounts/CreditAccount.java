package Accounts.ConcreteAccounts;

import Accounts.BaseAccount;
import Accounts.Account;
import Banks.Bank;
import Clients.Client;
import Exceptions.OutOfCreditLimitException;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class CreditAccount extends BaseAccount {
    private Double creditLimit;

    public CreditAccount(UUID id, Double creditLimit, Double commission, Client owner, Bank bank) {
        super(id, owner, bank);
        this.creditLimit = creditLimit;
        this.commission = commission;
    }

    @Override
    public void withdrawMoney(Double amount) {
        if (this.amount - amount < -(creditLimit)) throw new OutOfCreditLimitException();
        super.withdrawMoney(amount);
    }

    @Override
    public void transferMoney(Account receiver, Double amount) {
        if (this.amount - amount < creditLimit) throw new OutOfCreditLimitException();
        super.transferMoney(receiver, amount);
    }
}
