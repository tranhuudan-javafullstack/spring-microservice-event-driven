package com.huudan.accounts.query;

import lombok.Value;

@Value
public class FindAccountQuery {
    private final String mobileNumber;
}