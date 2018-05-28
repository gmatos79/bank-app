package doing.simplethings.bank.interactor;

import doing.simplethings.bank.api.boundary.CreateAccount;
import doing.simplethings.bank.api.requestmodel.CreateAccountRequest;
import doing.simplethings.bank.api.responsemodel.CreateAccountResponse;
import doing.simplethings.bank.api.responsemodel.OperationResponse;
import doing.simplethings.bank.domain.entity.Account;
import doing.simplethings.bank.domain.gateway.CreateAccountEntityGateway;

import java.math.BigDecimal;
import java.util.Collections;

public class CreateAccountImpl implements CreateAccount {
    private final CreateAccountEntityGateway createAccountEntityGateway;

    public CreateAccountImpl(CreateAccountEntityGateway createAccountEntityGateway) {
        this.createAccountEntityGateway = createAccountEntityGateway;
    }

    @Override
    public OperationResponse<CreateAccountResponse> execute(CreateAccountRequest request) {
        if (request.getInitialBalance().compareTo(BigDecimal.ZERO) == -1) {
            return OperationResponse.withErrors(
                    Collections.singletonList("The initial balance could not be negative")
            );
        }

        Account account = new Account(request.getName(), request.getInitialBalance());
        long newId = this.createAccountEntityGateway.save(account);

        return OperationResponse.withValue(
                new CreateAccountResponse(newId, account.getName(), account.getBalance())
        );
    }
}
