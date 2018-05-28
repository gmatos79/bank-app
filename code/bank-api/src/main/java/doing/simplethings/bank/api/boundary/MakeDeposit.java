package doing.simplethings.bank.api.boundary;

import doing.simplethings.bank.api.requestmodel.MakeDepositRequest;
import doing.simplethings.bank.api.responsemodel.MakeDepositResponse;
import doing.simplethings.bank.api.responsemodel.OperationResponse;

public interface MakeDeposit {
    OperationResponse<MakeDepositResponse> execute(MakeDepositRequest request);
}
