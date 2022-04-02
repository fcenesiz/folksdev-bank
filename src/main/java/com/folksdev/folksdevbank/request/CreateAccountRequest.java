package com.folksdev.folksdevbank.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountRequest extends BaseAccountRequest{

    private String id;

    @Override
    public String toString() {
        System.out.println(super.toString());
        return "CreateAccountRequest{" +
                "id='" + id + '\'' +
                '}';
    }
}
