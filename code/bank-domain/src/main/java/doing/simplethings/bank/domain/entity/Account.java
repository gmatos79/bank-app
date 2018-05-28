package doing.simplethings.bank.domain.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {
    private long id;
    private String name;
    private BigDecimal balance;

    public Account() {

    }

    public Account(long id, String name, BigDecimal balance) {
        super();
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public Account(String name, BigDecimal balance) {
        super();
        this.name = name;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account that = (Account) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, balance);
    }
}
