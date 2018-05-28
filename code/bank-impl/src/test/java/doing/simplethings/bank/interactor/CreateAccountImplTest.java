package doing.simplethings.bank.interactor;

import doing.simplethings.bank.api.boundary.CreateAccount;
import doing.simplethings.bank.api.requestmodel.CreateAccountRequest;
import doing.simplethings.bank.domain.entity.Account;
import doing.simplethings.bank.domain.gateway.CreateAccountEntityGateway;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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
        long accountId = this.createAccount.execute(createAccountRequest);

        //Then
        verify(this.createAccountEntityGateway, times(1)).save(any(Account.class));
        assertThat(accountId).isEqualTo(Long.MAX_VALUE);
    }
}
