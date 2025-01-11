package com.huudan.customer.command.event;

import lombok.Data;

/**
 * NOUN+VERB(PastTense)+Event
 */
@Data
public class CustomerCreatedEvent {

    private String customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private boolean activeSw;


}
