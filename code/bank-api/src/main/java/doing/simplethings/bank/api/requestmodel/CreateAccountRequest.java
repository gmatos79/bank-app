package doing.simplethings.bank.api.requestmodel;
import java.math.BigDecimal;
import java.util.Objects;


public class CreateAccountRequest {
    private final String name;
    private final BigDecimal initialBalance;

    public CreateAccountRequest(String name, BigDecimal initialBalance) {
        this.name = name;
        this.initialBalance = initialBalance;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateAccountRequest that = (CreateAccountRequest) o;
        return Objects.equals(name, that.name)
                && Objects.equals(initialBalance, that.initialBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, initialBalance);
    }
}
