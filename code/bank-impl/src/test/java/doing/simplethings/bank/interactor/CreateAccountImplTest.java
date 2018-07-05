package doing.simplethings.bank.interactor;

import doing.simplethings.bank.api.boundary.CreateAccount;
import doing.simplethings.bank.api.requestmodel.CreateAccountRequest;
import doing.simplethings.bank.api.responsemodel.CreateAccountResponse;
import doing.simplethings.bank.api.responsemodel.OperationResponse;
import doing.simplethings.bank.domain.entity.Account;
import doing.simplethings.bank.domain.gateway.AccountEntityGateway;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreateAccountImplTest {
    private AccountEntityGateway accountEntityGateway;
    private CreateAccount createAccount;

    @Before
    public void setup(){
        this.accountEntityGateway = mock(AccountEntityGateway.class);
        this.createAccount = new CreateAccountImpl(this.accountEntityGateway);
    }

    @Test
    public void shouldCreateAccount(){
        //Given
        when(this.accountEntityGateway.save(any(Account.class))).thenReturn(Long.MAX_VALUE);

        //When
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("John Doe", BigDecimal.TEN);
        OperationResponse<CreateAccountResponse> createAccountResponse =
                this.createAccount.execute(createAccountRequest);

        //Then
        assertThat(createAccountResponse).isNotNull();
        assertThat(createAccountResponse.hasError()).isFalse();
        assertThat(createAccountResponse.getValue().getId()).isEqualTo(Long.MAX_VALUE);
        assertThat(createAccountResponse.getValue().getName()).isEqualTo(createAccountRequest.getName());
        assertThat(createAccountResponse.getValue().getBalance()).isEqualTo(createAccountRequest.getInitialBalance());

        verify(this.accountEntityGateway, times(1)).save(any(Account.class));
    }

    @Test
    public void shouldReturnErrorWhenGivenNegativeBalance(){
        //Given
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("Luke", BigDecimal.valueOf(-1));

        //When
        OperationResponse<CreateAccountResponse> createAccountResponse =
                this.createAccount.execute(createAccountRequest);

        //Then
        assertThat(createAccountResponse).isNotNull();
        assertThat(createAccountResponse.hasError()).isTrue();
        assertThat(createAccountResponse.getErrors()).containsOnly("The initial balance could not be negative");

        verify(this.accountEntityGateway, never()).save(any(Account.class));
    }

    @Test
    public void shouldReturnErrorWhenGivenEmptyName() {
        this.shouldReturnErrorWhenGivenInvalidName("");
    }

    @Test
    public void shouldReturnErrorWhenGivenNullName() {
        this.shouldReturnErrorWhenGivenInvalidName(null);
    }

    private void shouldReturnErrorWhenGivenInvalidName(String name) {
        //Given
        CreateAccountRequest createAccountRequest = new CreateAccountRequest(name, BigDecimal.ONE);

        //When
        OperationResponse<CreateAccountResponse> createAccountResponse =
                this.createAccount.execute(createAccountRequest);

        //Then
        assertThat(createAccountResponse).isNotNull();
        assertThat(createAccountResponse.hasError()).isTrue();
        assertThat(createAccountResponse.getErrors()).containsOnly("The name could not be empty");

        verify(this.accountEntityGateway, never()).save(any(Account.class));
    }
}
