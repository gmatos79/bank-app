package doing.simplethings.bank.api.boundary;

import doing.simplethings.bank.api.requestmodel.CreateAccountRequest;

@FunctionalInterface
public interface CreateAccount {
    void execute(CreateAccountRequest request);
}