package com.folksdev.folksdevbank;

import com.folksdev.folksdevbank.model.Account;
import com.folksdev.folksdevbank.model.City;
import com.folksdev.folksdevbank.model.Currency;
import com.folksdev.folksdevbank.model.Customer;
import com.folksdev.folksdevbank.repository.AccountRepository;
import com.folksdev.folksdevbank.repository.CustomerRepository;
import com.folksdev.folksdevbank.request.CreateCustomerRequest;
import com.folksdev.folksdevbank.service.AccountService;
import com.folksdev.folksdevbank.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
@EnableRabbit
public class BankApp implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(BankApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Customer c1 = Customer.builder()
                .name("Cagri")
                .city(City.ISTANBUL)
                .address("Ev")
                .dateOfBirth(1988)
                .build();
        Customer c2 = Customer.builder()
                .name("Semih")
                .city(City.ANKARA)
                .address("İş")
                .dateOfBirth(1996)
                .build();
        Customer c3 = Customer.builder()
                .name("Osman")
                .city(City.KOCAELI)
                .address("Ofis")
                .dateOfBirth(2003)
                .build();

        customerRepository.saveAllAndFlush(Arrays.asList(c1, c2 ,c3));

        Account a1 = Account.builder()
                .customerId(c1.getId())
                .city(City.ISTANBUL)
                .balance(1320.0)
                .currency(Currency.TRY)
                .build();
        Account a2 = Account.builder()
                .customerId(c2.getId())
                .city(City.ANKARA)
                .balance(720.0)
                .currency(Currency.TRY)
                .build();
        Account a3 = Account.builder()
                .customerId(c3.getId())
                .city(City.KOCAELI)
                .balance(5170.0)
                .currency(Currency.TRY)
                .build();

        accountRepository.saveAll(Arrays.asList(a1, a2, a3));

    }
}

// Katman Sırası ----------
//     Yukarıdan aşağı doğru çağırma işlemi yapılır.
// Controller - API
//     Dto : Controller Dto kullanarak service'i çağırır.
// Service
// Repository
//     Model : Repository Model kullanarak Database'i çağırır.
// Database
// ------------------------