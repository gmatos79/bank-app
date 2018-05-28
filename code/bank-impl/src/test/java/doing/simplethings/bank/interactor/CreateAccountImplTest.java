package doing.simplethings.bank.interactor;

import doing.simplethings.bank.api.boundary.CreateAccount;
import doing.simplethings.bank.api.requestmodel.CreateAccountRequest;
import doing.simplethings.bank.api.responsemodel.CreateAccountResponse;
import doing.simplethings.bank.api.responsemodel.OperationResponse;
import doing.simplethings.bank.domain.entity.Account;
import doing.simplethings.bank.domain.gateway.CreateAccountEntityGateway;
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
    private CreateAccountEntityGateway createAccountEntityGateway;
    private CreateAccount createAccount;

    @Before
    public void setup(){
        this.createAccountEntityGateway = mock(CreateAccountEntityGateway.class);
        this.createAccount = new CreateAccountImpl(this.createAccountEntityGateway);
    }

    @Test
    public void shouldCreateAccount(){
        //Given
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("John Doe", BigDecimal.TEN);
        when(this.createAccountEntityGateway.save(any(Account.class))).thenReturn(Long.MAX_VALUE);

        //When
        OperationResponse<CreateAccountResponse> createAccountResponse =
                this.createAccount.execute(createAccountRequest);

        //Then
        assertThat(createAccountResponse).isNotNull();
        assertThat(createAccountResponse.hasError()).isFalse();
        assertThat(createAccountResponse.getValue().getId()).isEqualTo(Long.MAX_VALUE);
        assertThat(createAccountResponse.getValue().getName()).isEqualTo(createAccountRequest.getName());
        assertThat(createAccountResponse.getValue().getBalance()).isEqualTo(createAccountRequest.getInitialBalance());

        verify(this.createAccountEntityGateway, times(1)).save(any(Account.class));
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

        verify(this.createAccountEntityGateway, never()).save(any(Account.class));
    }
}
