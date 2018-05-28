package doing.simplethings.bank.interactor;

import doing.simplethings.bank.api.boundary.CreateAccount;
import doing.simplethings.bank.api.requestmodel.CreateAccountRequest;
import doing.simplethings.bank.api.responsemodel.CreateAccountResponse;
import doing.simplethings.bank.domain.entity.Account;
import doing.simplethings.bank.domain.gateway.CreateAccountEntityGateway;

public class CreateAccountImpl implements CreateAccount {
    private final CreateAccountEntityGateway createAccountEntityGateway;

    public CreateAccountImpl(CreateAccountEntityGateway createAccountEntityGateway) {
        this.createAccountEntityGateway = createAccountEntityGateway;
    }

    @Override
    public CreateAccountResponse execute(CreateAccountRequest request) {
        Account account = new Account(request.getName(), request.getInitialBalance());
        long newId = this.createAccountEntityGateway.save(account);
        return new CreateAccountResponse(newId, account.getName(), account.getBalance());
    }
}
