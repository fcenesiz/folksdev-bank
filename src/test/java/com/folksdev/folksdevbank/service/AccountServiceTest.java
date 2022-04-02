package com.folksdev.folksdevbank.service;

import com.folksdev.folksdevbank.dto.AccountDto;
import com.folksdev.folksdevbank.dto.AccountDtoConverter;
import com.folksdev.folksdevbank.model.Account;
import com.folksdev.folksdevbank.model.City;
import com.folksdev.folksdevbank.model.Currency;
import com.folksdev.folksdevbank.model.Customer;
import com.folksdev.folksdevbank.repository.AccountRepository;
import com.folksdev.folksdevbank.request.CreateAccountRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.amqp.core.AmqpTemplate;

public class AccountServiceTest {

    //Test Unit
    private AccountService accountService;

    private AccountRepository accountRepository;
    private CustomerService customerService;
    private AccountDtoConverter accountDtoConverter;


    @Before
    public void setUp() {
        accountRepository = Mockito.mock(AccountRepository.class);
        customerService = Mockito.mock(CustomerService.class);
        accountDtoConverter = Mockito.mock(AccountDtoConverter.class);

        accountService = new AccountService(accountRepository, customerService, accountDtoConverter);
    }

    @Test
    public void shouldReturnValidAccountDto_whenCreateAccountCalledWithValidRequest() {

        // given
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("12");
        createAccountRequest.setId("12");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCurrency(Currency.TRY);

        Customer customer = Customer.builder()
                .id("12345")
                .name("Osman")
                .address("Ev")
                .city(City.ANKARA)
                .dateOfBirth(1998)
                .build();

        Account account = Account.builder()
                .id(createAccountRequest.getId())
                .customerId(createAccountRequest.getCustomerId())
                .balance(createAccountRequest.getBalance())
                .city(createAccountRequest.getCity())
                .currency(createAccountRequest.getCurrency())
                .build();

        AccountDto accountDto = AccountDto.builder()
                .id("12")
                .customerId("12345")
                .balance(100.0)
                .currency(Currency.TRY)
                .build();

        // when/then
        Mockito.when(customerService.getCustomerById("12345"))
                .thenReturn(customer);

        Mockito.when(accountRepository.save(account))
                .thenReturn(account);

        Mockito.when(accountDtoConverter.convert(account))
                .thenReturn(accountDto);

        AccountDto result = accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result, accountDto);

        Mockito.verify(customerService).getCustomerById("12345");
        Mockito.verify(accountRepository).save(account);
        Mockito.verify(accountDtoConverter).convert(account);

    }

    @Test(expected = RuntimeException.class)
    public void shouldReturnEmptyAccountDto_whenCreateAccountCalledWithNonExistCustomer() {

        // given
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCurrency(Currency.TRY);

        // when/then
        Mockito.when(customerService.getCustomerById("12345"))
                .thenThrow(RuntimeException.class);

        AccountDto expectedAccountDto = AccountDto.builder().build();;
        AccountDto result = accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result, expectedAccountDto);

        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoInteractions(accountDtoConverter);
    }

    @Test(expected = RuntimeException.class)
    public void shouldReturnEmptyAccountDto_whenCreateAccountCalledWithCustomerWithoutId() {
        // given
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("12");
        createAccountRequest.setId("12");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCurrency(Currency.TRY);

        // bu sorunu trim() fonksiyonu ile çözdük
        // when/then
        Mockito.when(customerService.getCustomerById("12345"))
                .thenThrow(RuntimeException.class);

        AccountDto expectedAccountDto = AccountDto.builder().build();;
        AccountDto result = accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result, expectedAccountDto);

    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeException_whenCreateAccountCalledWithNonExistCustomer() {
        // given
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("12");
        createAccountRequest.setId("12");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCurrency(Currency.TRY);



        // when/then
        Mockito.when(customerService.getCustomerById("12345"))
                .thenThrow(RuntimeException.class);

        AccountDto expectedAccountDto = AccountDto.builder().build();

        AccountDto result = accountService.createAccount(createAccountRequest);

        Assert.assertEquals(expectedAccountDto, result);

        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoInteractions(accountDtoConverter);

    }

}
