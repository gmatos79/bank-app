package doing.simplethings.bank.domain.gateway;

import doing.simplethings.bank.domain.entity.Account;

public interface AccountEntityGateway {
    long save(Account account);
    Account getById(long id);
    boolean existsById(long id);
}
