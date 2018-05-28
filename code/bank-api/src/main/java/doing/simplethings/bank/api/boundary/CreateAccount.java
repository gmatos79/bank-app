package doing.simplethings.bank.api.boundary;

import doing.simplethings.bank.api.requestmodel.CreateAccountRequest;
import doing.simplethings.bank.api.responsemodel.CreateAccountResponse;
import doing.simplethings.bank.api.responsemodel.OperationResponse;

public interface CreateAccount {
    OperationResponse<CreateAccountResponse> execute(CreateAccountRequest request);
}