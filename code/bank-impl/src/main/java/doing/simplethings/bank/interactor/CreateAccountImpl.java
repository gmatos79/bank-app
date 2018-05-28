package doing.simplethings.bank.interactor;

import doing.simplethings.bank.api.boundary.CreateAccount;
import doing.simplethings.bank.api.requestmodel.CreateAccountRequest;
import doing.simplethings.bank.api.responsemodel.CreateAccountResponse;
import doing.simplethings.bank.api.responsemodel.OperationResponse;
import doing.simplethings.bank.domain.entity.Account;
import doing.simplethings.bank.domain.gateway.AccountEntityGateway;

import java.math.BigDecimal;
import java.util.Collections;

public class CreateAccountImpl implements CreateAccount {
    private final AccountEntityGateway accountEntityGateway;

    public CreateAccountImpl(AccountEntityGateway accountEntityGateway) {
        this.accountEntityGateway = accountEntityGateway;
    }

    @Override
    public OperationResponse<CreateAccountResponse> execute(CreateAccountRequest request) {
        OperationResponse<CreateAccountResponse> createAccountResponse;

        if (hasNegativeBalance(request)) {
            createAccountResponse = OperationResponse.withErrors(
                    Collections.singletonList("The initial balance could not be negative")
            );
        } else {
            Account account = new Account(request.getName(), request.getInitialBalance());
            long accountId = this.accountEntityGateway.save(account);

            createAccountResponse = OperationResponse.withValue(
                    new CreateAccountResponse(accountId, account.getName(), account.getBalance())
            );
        }

        return createAccountResponse;
    }

    private boolean hasNegativeBalance(CreateAccountRequest request) {
        return request.getInitialBalance().compareTo(BigDecimal.ZERO) == -1;
    }
}
