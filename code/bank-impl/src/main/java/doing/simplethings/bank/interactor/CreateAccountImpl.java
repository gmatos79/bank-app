package doing.simplethings.bank.interactor;

import doing.simplethings.bank.api.boundary.CreateAccount;
import doing.simplethings.bank.api.requestmodel.CreateAccountRequest;
import doing.simplethings.bank.domain.gateway.CreateAccountEntityGateway;

public class CreateAccountImpl implements CreateAccount {
    private final CreateAccountEntityGateway createAccountEntityGateway;

    public CreateAccountImpl(CreateAccountEntityGateway createAccountEntityGateway) {
        this.createAccountEntityGateway = createAccountEntityGateway;
    }

    @Override
    public long execute(CreateAccountRequest request) {
        return -1;
    }
}
