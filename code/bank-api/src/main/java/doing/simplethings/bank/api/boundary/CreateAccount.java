package doing.simplethings.bank.api.boundary;

import doing.simplethings.bank.api.requestmodel.CreateAccountRequest;
import doing.simplethings.bank.api.responsemodel.CreateAccountResponse;

public interface CreateAccount {
    CreateAccountResponse execute(CreateAccountRequest request);
}