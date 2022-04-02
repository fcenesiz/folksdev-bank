package com.folksdev.folksdevbank.request;

import com.folksdev.folksdevbank.dto.CityDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseCustomerRequest {

    private String name;
    private Integer dateOfBirth;
    private CityDto city;
    private String address;

}
