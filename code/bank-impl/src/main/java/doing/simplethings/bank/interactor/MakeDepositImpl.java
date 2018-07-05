package doing.simplethings.bank.interactor;

import doing.simplethings.bank.api.boundary.MakeDeposit;
import doing.simplethings.bank.api.requestmodel.MakeDepositRequest;
import doing.simplethings.bank.api.responsemodel.MakeDepositResponse;
import doing.simplethings.bank.api.responsemodel.OperationResponse;
import doing.simplethings.bank.domain.entity.Account;
import doing.simplethings.bank.domain.gateway.AccountEntityGateway;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MakeDepositImpl implements MakeDeposit {
    private final AccountEntityGateway accountEntityGateway;

    public MakeDepositImpl(AccountEntityGateway accountEntityGateway) {
        this.accountEntityGateway = accountEntityGateway;
    }

    @Override
    public OperationResponse<MakeDepositResponse> execute(MakeDepositRequest request) {
        OperationResponse<MakeDepositResponse> makeDepositResponse;

        List<String> validationResult = this.validateRequestData(request);

        if (validationResult.isEmpty()) {
            Account account = this.accountEntityGateway.getById(request.getAccountId());
            account.setBalance(account.getBalance().add(request.getValue()));

            makeDepositResponse = OperationResponse.withValue(
                    new MakeDepositResponse(UUID.randomUUID(), account.getId(), request.getValue())
            );

            this.accountEntityGateway.save(account);
        } else {
            makeDepositResponse = OperationResponse.withErrors(validationResult);
        }

        return makeDepositResponse;
    }

    private List<String> validateRequestData(MakeDepositRequest request) {
        List<String> errors = new ArrayList<>();

        if (request.getValue().compareTo(BigDecimal.ZERO) == -1) {
            errors.add("The value could not be negative");
        }

        if (!this.accountEntityGateway.existsById(request.getAccountId())) {
            errors.add(String.format("Account not found: %s", request.getAccountId()));
        }

        return errors;
    }

}
