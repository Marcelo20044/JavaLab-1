package Accounts;

import Banks.Bank;
import Transactions.Transaction;

/**
 * Интерфейс банковского аккаунта
 *
 * @autor Марк Хвостенко
 */
public interface Account {
    /**
     * Метод снятия денег со счета
     *
     * @param amount сумма снятия
     */
    void withdrawMoney(Double amount);

    /**
     * Метод пополнения счета деньгами
     *
     * @param amount сумма пополнения
     */
    void replenishAccount(Double amount);

    /**
     * Метод перевода денег с одного счета на другой
     *
     * @param receiver получатель денег
     * @param amount   сумма перевода
     */
    void transferMoney(Account receiver, Double amount);

    /**
     * Метод ежедневного начисления процента на остаток
     */
    void calculateInterest();

    /**
     * Метод ежемесячной выплаты процента на остаток
     */
    void payInterest();

    /**
     * Метод ежемесячного вычета коммиссии
     */
    void deductCommission();

    /**
     * Метод добалвения новой транзакции в историю транзакций аккаунта
     *
     * @param transaction транзакция
     */
    void addTransaction(Transaction transaction);

    /**
     * Метод удаления транзакции из истории транзакций аккаунта
     *
     * @param transaction транзакция
     */
    void removeTransaction(Transaction transaction);

    void setInterest(Double interest);

    void setCommission(Double commission);

    Bank getBank();

    Double getAmount();
}
