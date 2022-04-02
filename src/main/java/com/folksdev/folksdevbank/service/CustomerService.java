package com.folksdev.folksdevbank.service;

import com.folksdev.folksdevbank.dto.CustomerDto;
import com.folksdev.folksdevbank.dto.CustomerDtoConverter;
import com.folksdev.folksdevbank.model.City;
import com.folksdev.folksdevbank.model.Customer;
import com.folksdev.folksdevbank.repository.CustomerRepository;
import com.folksdev.folksdevbank.request.CreateCustomerRequest;
import com.folksdev.folksdevbank.request.UpdateCustomerRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;

    public CustomerDto createCustomer(CreateCustomerRequest customerRequest) {
        Customer customer = Customer.builder()
                .name(customerRequest.getName())
                .address(customerRequest.getAddress())
                .dateOfBirth(customerRequest.getDateOfBirth())
                .city(City.valueOf(customerRequest.getCity().name()))
                .build();

        customerRepository.save(customer);

        return customerDtoConverter.convert(customer);
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();

        List<CustomerDto> customerDtoList = new ArrayList<>();

        customerList.forEach(it ->
                customerDtoList.add(customerDtoConverter.convert(it))
        );
        return customerDtoList;
    }

    public CustomerDto getCustomerDtoById(String id) {
        Optional<Customer> customer = customerRepository.findCustomerById(id);
        return customer.map(customerDtoConverter::convert).orElse(new CustomerDto());
    }

    public void deleteCustomerById(String id) {
        customerRepository.deleteById(id);
    }

    public CustomerDto updateCustomerById(String id, UpdateCustomerRequest customerRequest) {
        Optional<Customer> customer = customerRepository.findCustomerById(id);
        customer.ifPresent( it -> {
            it.setName(customerRequest.getName());
            it.setAddress(customerRequest.getAddress());
            it.setCity(City.valueOf(customerRequest.getCity().name()));
            it.setDateOfBirth(customerRequest.getDateOfBirth());
            customerRepository.save(it);
        });

        return customer.map(customerDtoConverter::convert).orElse(new CustomerDto());
    }

    protected Customer getCustomerById(String id){
        return customerRepository.findById(id).orElse(new Customer());
    }
}
