package com.folksdev.folksdevbank.request;

import com.folksdev.folksdevbank.model.City;
import com.folksdev.folksdevbank.model.Currency;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseAccountRequest {

    private String customerId;
    private Double balance;
    private Currency currency;
    private City city;

    @Override
    public String toString() {
        return "BaseAccountRequest{" +
                "customerId='" + customerId + '\'' +
                ", balance=" + balance +
                ", currency=" + currency +
                ", city=" + city +
                '}';
    }
}
