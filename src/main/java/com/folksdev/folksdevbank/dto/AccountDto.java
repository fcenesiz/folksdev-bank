package com.folksdev.folksdevbank.dto;

import com.folksdev.folksdevbank.model.Account;
import com.folksdev.folksdevbank.model.City;
import com.folksdev.folksdevbank.model.Currency;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class AccountDto {

    private String id;
    private String customerId;
    private Double balance;
    private Currency currency;


}
