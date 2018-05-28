package doing.simplethings.bank.api.requestmodel;

import java.math.BigDecimal;
import java.util.Objects;

public class MakeDepositRequest {
    private final long accountId;
    private final BigDecimal value;

    public MakeDepositRequest(long accountId, BigDecimal value) {
        this.accountId = accountId;
        this.value = value;
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
        MakeDepositRequest that = (MakeDepositRequest) o;
        return Objects.equals(accountId, that.accountId)
                && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, value);
    }
}