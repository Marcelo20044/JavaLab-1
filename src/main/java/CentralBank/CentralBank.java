package CentralBank;

import Accounts.Account;
import Banks.AccountsTerms;
import Banks.Bank;

import java.util.ArrayList;

public class CentralBank {
    private static CentralBank instance;
    private final Double transferCommission = 0.1;
    private final ArrayList<Bank> banks;

    private CentralBank() {
        banks = new ArrayList<>();
    }

    public static CentralBank getInstance() {
        if (instance == null) instance = new CentralBank();
        return instance;
    }

    public void transferMoneyToAnotherBank(Double amount, Account sender, Account receiver) {
        sender.withdrawMoney(amount * transferCommission);
        receiver.replenishAccount(amount);
    }

    public Bank createBank(String name, AccountsTerms accountsTerms) {
        var bank = new Bank(name, accountsTerms);
        banks.add(bank);
        return bank;
    }

    public void calculateInterest() {
        banks.forEach(Bank::calculateInterestForAllAccounts);
    }

    public void payInterest() {
        banks.forEach(Bank::payInterestForAllAccounts);
    }

    public void deductCommission() {
        banks.forEach(Bank::deductCommissionForAllAccounts);
    }

    public void timeMachine(int days) {
        for (int i = 1; i <= days; i++) {
            calculateInterest();
            if (i % 30 == 0) {
                payInterest();
                deductCommission();
            }
        }
    }
}
