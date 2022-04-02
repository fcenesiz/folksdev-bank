package com.folksdev.folksdevbank.service;

import com.folksdev.folksdevbank.dto.AccountDto;
import com.folksdev.folksdevbank.dto.AccountDtoConverter;
import com.folksdev.folksdevbank.model.Account;
import com.folksdev.folksdevbank.model.Customer;
import com.folksdev.folksdevbank.repository.AccountRepository;
import com.folksdev.folksdevbank.repository.CustomerRepository;
import com.folksdev.folksdevbank.request.CreateAccountRequest;
import com.folksdev.folksdevbank.request.UpdateAccountRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDtoConverter accountDtoConverter;



    public AccountDto createAccount(CreateAccountRequest accountRequest){

        Customer customer = customerService.getCustomerById(accountRequest.getCustomerId());

        if (customer.getId() == null || customer.getId().trim().equals("")){
            return AccountDto.builder().build();
        }

        Account account = Account.builder()
                .id(accountRequest.getId())
                .customerId(accountRequest.getCustomerId())
                .balance(accountRequest.getBalance())
                .currency(accountRequest.getCurrency())
                .city(accountRequest.getCity())
                .build();

        Account savedAccount = accountRepository.save(account);

        return accountDtoConverter.convert(savedAccount);
    }

    public AccountDto updateAccountById(String id, UpdateAccountRequest request){
        Customer customer = customerService.getCustomerById(request.getCustomerId());
        if (customer.getId() == null || customer.getId().equals("")){
            throw new RuntimeException("Customer Not Found!");
        }

        Optional<Account> accountOpt = accountRepository.findById(id);
        accountOpt.ifPresent( account -> {
            account.setCustomerId(request.getCustomerId());
            account.setBalance(request.getBalance());
            account.setCity(request.getCity());
            account.setCurrency(request.getCurrency());
            accountRepository.save(account);
        });

        return accountOpt.map(accountDtoConverter::convert).orElse(new AccountDto());
    }

    public List<AccountDto> getAllAccounts(){
        List<Account> accountList = accountRepository.findAll();
        return accountList.stream().map(accountDtoConverter::convert).collect(Collectors.toList());
    }

    public AccountDto getAccountDtoById(String id){
        return accountRepository.findById(id)
                .map(accountDtoConverter::convert)
                .orElse(new AccountDto());
    }

    public void deleteAccountById(String id) {
        accountRepository.deleteById(id);
    }

    public AccountDto withdrawMoney(String id, Double amount){
        Optional<Account> accountOpt = accountRepository.findById(id);
        accountOpt.ifPresent(account -> {
            if (account.getBalance() >= amount){
                account.setBalance(account.getBalance() - amount);
                accountRepository.save(account);
            }else {
                System.out.println("Insufficient funds -> accountId:" + id + ", balance: " + account.getBalance() + ", amount: " + amount + "!");
            }
        });

        return accountOpt.map(accountDtoConverter::convert).orElse(new AccountDto());
    }

    public AccountDto addMoney(String id, Double amount){
        Optional<Account> accountOpt = accountRepository.findById(id);
        accountOpt.ifPresent(account -> {
            account.setBalance(account.getBalance() + amount);
            accountRepository.save(account);
        });

        return accountOpt.map(accountDtoConverter::convert).orElse(new AccountDto());
    }
}
