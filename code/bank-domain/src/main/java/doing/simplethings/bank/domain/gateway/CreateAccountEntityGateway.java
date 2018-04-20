package doing.simplethings.bank.domain.gateway;

import doing.simplethings.bank.domain.entity.Account;

public interface CreateAccountEntityGateway {
    long create(Account account);
}
