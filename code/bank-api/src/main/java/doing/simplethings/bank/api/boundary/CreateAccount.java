package doing.simplethings.bank.api.boundary;

import doing.simplethings.bank.api.requestmodel.CreateAccountRequest;

@FunctionalInterface
public interface CreateAccount {
    long execute(CreateAccountRequest request);
}