package doing.simplethings.bank.interactor;

import doing.simplethings.bank.api.boundary.CreateAccount;
import doing.simplethings.bank.api.requestmodel.CreateAccountRequest;
import doing.simplethings.bank.api.responsemodel.CreateAccountResponse;
import doing.simplethings.bank.api.responsemodel.OperationResponse;
import doing.simplethings.bank.domain.entity.Account;
import doing.simplethings.bank.domain.gateway.AccountEntityGateway;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreateAccountImpl implements CreateAccount {
    private final AccountEntityGateway accountEntityGateway;

    public CreateAccountImpl(AccountEntityGateway accountEntityGateway) {
        this.accountEntityGateway = accountEntityGateway;
    }

    @Override
    public OperationResponse<CreateAccountResponse> execute(CreateAccountRequest request) {
        OperationResponse<CreateAccountResponse> createAccountResponse;

        List<String> validationResult = this.validateRequestData(request);

        if (validationResult.isEmpty()) {
            Account account = new Account(request.getName(), request.getInitialBalance());
            long accountId = this.accountEntityGateway.save(account);

            createAccountResponse = OperationResponse.withValue(
                    new CreateAccountResponse(accountId, account.getName(), account.getBalance())
            );
        } else {
            createAccountResponse = OperationResponse.withErrors(validationResult);
        }

        return createAccountResponse;
    }

    private List<String> validateRequestData(CreateAccountRequest request) {
        List<String> errors = new ArrayList<>();

        if (request.getName() == null || request.getName().trim().equals("")) {
            errors.add("The name could not be empty");
        }

        if (request.getInitialBalance().compareTo(BigDecimal.ZERO) == -1) {
            errors.add("The initial balance could not be negative");
        }

        return errors;
    }
}
