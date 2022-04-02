package com.folksdev.folksdevbank.request;

import com.folksdev.folksdevbank.dto.CityDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerRequest extends BaseCustomerRequest{

    private String id;

}
