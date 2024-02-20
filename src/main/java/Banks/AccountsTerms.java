package Banks;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.TreeMap;

/**
 * Класс, описывающий условия банковских счетов
 */
@Setter
@Getter
public class AccountsTerms {
    private Double annualInterest;
    private Double commission;
    private Double creditLimit;
    private Integer depositAccountTermInMonths;
    private Double maxTransactionAmountForSuspiciousClientAccounts;

    /**
     * Эта мапа хранит в себе информацию о процентах на остаток для разных депозитов
     * <br>
     * <b>Key</b> - значение депозита, начиная с которого, процент на остаток будет равен <b>value</b>
     * <br>
     * !Мапа должна сортироваться по ключу по возрастанию
     */
    private final Map<Double, Double> annualInterestsForDeposits;

    public AccountsTerms
            (Double annualInterest,
             Double commission,
             Double creditLimit,
             Integer depositAccountTermInMonths,
             Double maxTransactionAmountForSuspiciousClientAccounts,
             TreeMap<Double, Double> annualInterestsForDeposits) {
        this.annualInterest = annualInterest;
        this.commission = commission;
        this.creditLimit = creditLimit;
        this.depositAccountTermInMonths = depositAccountTermInMonths;
        this.maxTransactionAmountForSuspiciousClientAccounts = maxTransactionAmountForSuspiciousClientAccounts;
        this.annualInterestsForDeposits = annualInterestsForDeposits;
    }

    /**
     * Этот метод для конкретного депозита возвращает соответствующий ему процент на остаток
     * @param deposit - депозит
     */
    public Double getAnnualInterestForDeposit(Double deposit) {
        return annualInterestsForDeposits.keySet().stream()
                .filter(dep -> dep <= deposit)
                .findFirst()
                .map(annualInterestsForDeposits::get)
                .orElse(0D);
    }
}
