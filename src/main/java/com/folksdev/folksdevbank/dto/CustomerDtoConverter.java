package com.folksdev.folksdevbank.dto;

import com.folksdev.folksdevbank.model.Customer;
import org.springframework.stereotype.Component;

// bu sınıfı service tarafında kod yazımını kısaltmak için yaptık
@Component // servise benzer bir yapı, kendi içinde işlem yapan ancak başka katmandakullanılan
public class CustomerDtoConverter {

    public CustomerDto convert(Customer customer){
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .city(CityDto.valueOf(customer.getCity().name()))
                .dateOfBirth(customer.getDateOfBirth())
                .build();
    }

}
