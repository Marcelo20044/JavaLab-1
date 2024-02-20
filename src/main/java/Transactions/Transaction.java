package Transactions;

import java.util.UUID;

public interface Transaction {
    void execute();

    void rollback();

    UUID getTransactionId();
}
