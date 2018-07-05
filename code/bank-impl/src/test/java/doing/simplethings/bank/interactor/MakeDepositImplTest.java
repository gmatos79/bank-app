package doing.simplethings.bank.interactor;

import doing.simplethings.bank.api.boundary.MakeDeposit;
import doing.simplethings.bank.api.requestmodel.MakeDepositRequest;
import doing.simplethings.bank.api.responsemodel.MakeDepositResponse;
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

public class MakeDepositImplTest {
    private AccountEntityGateway accountEntityGateway;
    private MakeDeposit makeDeposit;

    @Before
    public void setup(){
        this.accountEntityGateway = mock(AccountEntityGateway.class);
        this.makeDeposit = new MakeDepositImpl(this.accountEntityGateway);
    }

    @Test
    public void shouldMakeDeposit() {
        //Given
        long accountId = Long.MIN_VALUE;
        BigDecimal value = BigDecimal.TEN;
        String name = "John Doe";
        Account initialAccount = new Account(accountId, name, BigDecimal.ZERO);
        Account afterDepositAccount = new Account(accountId, name, BigDecimal.TEN);

        when(this.accountEntityGateway.existsById(accountId)).thenReturn(true);
        when(this.accountEntityGateway.getById(accountId)).thenReturn(initialAccount);
        when(this.accountEntityGateway.save(afterDepositAccount)).thenReturn(Long.MAX_VALUE);

        //When
        MakeDepositRequest makeDepositRequest = new MakeDepositRequest(accountId, value);
        OperationResponse<MakeDepositResponse> makeDepositResponse =
                this.makeDeposit.execute(makeDepositRequest);

        //Then
        assertThat(makeDepositResponse).isNotNull();
        assertThat(makeDepositResponse.hasError()).isFalse();
        assertThat(makeDepositResponse.getValue().getAccountId()).isEqualTo(accountId);
        assertThat(makeDepositResponse.getValue().getTransactionId()).isNotNull();
        assertThat(makeDepositResponse.getValue().getValue()).isEqualTo(value);

        verify(this.accountEntityGateway, times(1)).getById(accountId);
        verify(this.accountEntityGateway, times(1)).save(afterDepositAccount);
    }

    @Test
    public void shouldReturnErrorWhenGivenAInvalidAccountId() {
        //Given
        long invalidAccountId = Long.MAX_VALUE;

        when(this.accountEntityGateway.existsById(invalidAccountId)).thenReturn(false);

        //When
        MakeDepositRequest makeDepositRequest = new MakeDepositRequest(invalidAccountId, BigDecimal.ONE);
        OperationResponse<MakeDepositResponse> makeDepositResponse =
                this.makeDeposit.execute(makeDepositRequest);

        //Then
        assertThat(makeDepositResponse).isNotNull();
        assertThat(makeDepositResponse.hasError()).isTrue();
        assertThat(makeDepositResponse.getErrors()).containsOnly(
                String.format("Account not found: %s", invalidAccountId));

        verify(this.accountEntityGateway, times(1)).existsById(invalidAccountId);
        verify(this.accountEntityGateway, never()).save(any(Account.class));
    }

    @Test
    public void shouldReturnErrorWhenGivenNegativeBalance() {
        //Given
        when(this.accountEntityGateway.existsById(any(Long.class))).thenReturn(true);
        MakeDepositRequest makeDepositRequest = new MakeDepositRequest(Long.MIN_VALUE, BigDecimal.valueOf(-1));

        //When
        OperationResponse<MakeDepositResponse> makeDepositResponse = this.makeDeposit.execute(makeDepositRequest);

        //Then
        assertThat(makeDepositResponse).isNotNull();
        assertThat(makeDepositResponse.hasError()).isTrue();
        assertThat(makeDepositResponse.getErrors()).containsOnly("The value could not be negative");

        verify(this.accountEntityGateway, never()).save(any(Account.class));
    }
}