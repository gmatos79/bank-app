package doing.simplethings.bank.api.responsemodel;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class MakeDepositResponse {
    private final UUID transactionId;
    private final long accountId;
    private final BigDecimal value;

    public MakeDepositResponse(UUID transactionId, long accountId, BigDecimal value) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.value = value;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public long getAccountId() {
        return accountId;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MakeDepositResponse that = (MakeDepositResponse) o;
        return Objects.equals(transactionId, that.transactionId)
                && Objects.equals(accountId, that.accountId)
                && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, accountId, value);
    }
}
