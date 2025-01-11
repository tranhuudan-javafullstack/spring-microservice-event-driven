package com.huudan.customer.command.aggregate;

import com.huudan.common.command.RollbackCusMobNumCommand;
import com.huudan.common.command.UpdateCusMobNumCommand;
import com.huudan.common.event.CusMobNumRollbackedEvent;
import com.huudan.common.event.CusMobNumUpdatedEvent;
import com.huudan.customer.command.CreateCustomerCommand;
import com.huudan.customer.command.DeleteCustomerCommand;
import com.huudan.customer.command.UpdateCustomerCommand;
import com.huudan.customer.command.event.CustomerCreatedEvent;
import com.huudan.customer.command.event.CustomerDeletedEvent;
import com.huudan.customer.command.event.CustomerUpdatedEvent;
import com.huudan.customer.repository.CustomerRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class CustomerAggregate {

    @AggregateIdentifier
    private String customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private boolean activeSw;
    private String errorMsg;

    public CustomerAggregate() {

    }

    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand createCustomerCommand, CustomerRepository customerRepository) {
        CustomerCreatedEvent customerCreatedEvent = new CustomerCreatedEvent();
        BeanUtils.copyProperties(createCustomerCommand, customerCreatedEvent);
        AggregateLifecycle.apply(customerCreatedEvent);
    }

    @EventSourcingHandler
    public void on(CustomerCreatedEvent customerCreatedEvent) {
        this.customerId = customerCreatedEvent.getCustomerId();
        this.name = customerCreatedEvent.getName();
        this.email= customerCreatedEvent.getEmail();
        this.mobileNumber = customerCreatedEvent.getMobileNumber();
        this.activeSw = customerCreatedEvent.isActiveSw();
    }

    @CommandHandler
    public void handle(UpdateCustomerCommand updateCustomerCommand, EventStore eventStore) {
        CustomerUpdatedEvent customerUpdatedEvent = new CustomerUpdatedEvent();
        BeanUtils.copyProperties(updateCustomerCommand, customerUpdatedEvent);
        AggregateLifecycle.apply(customerUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(CustomerUpdatedEvent customerUpdatedEvent) {
        this.name = customerUpdatedEvent.getName();
        this.email= customerUpdatedEvent.getEmail();
    }

    @CommandHandler
    public void handle(DeleteCustomerCommand deleteCustomerCommand) {
        CustomerDeletedEvent customerDeletedEvent = new CustomerDeletedEvent();
        BeanUtils.copyProperties(deleteCustomerCommand, customerDeletedEvent);
        AggregateLifecycle.apply(customerDeletedEvent);
    }

    @EventSourcingHandler
    public void on(CustomerDeletedEvent customerDeletedEvent) {
        this.activeSw = customerDeletedEvent.isActiveSw();
    }

    @CommandHandler
    public void handle(UpdateCusMobNumCommand updateCusMobNumCommand) {
        CusMobNumUpdatedEvent cusMobNumUpdatedEvent = new CusMobNumUpdatedEvent();
        BeanUtils.copyProperties(updateCusMobNumCommand, cusMobNumUpdatedEvent);
        AggregateLifecycle.apply(cusMobNumUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(CusMobNumUpdatedEvent cusMobNumUpdatedEvent) {
        this.mobileNumber = cusMobNumUpdatedEvent.getNewMobileNumber();
    }

    @CommandHandler
    public void handle(RollbackCusMobNumCommand rollbackCusMobNumCommand) {
        CusMobNumRollbackedEvent cusMobNumRollbackedEvent = new CusMobNumRollbackedEvent();
        BeanUtils.copyProperties(rollbackCusMobNumCommand, cusMobNumRollbackedEvent);
        AggregateLifecycle.apply(cusMobNumRollbackedEvent);
    }

    @EventSourcingHandler
    public void on(CusMobNumRollbackedEvent cusMobNumRollbackedEvent) {
        this.mobileNumber = cusMobNumRollbackedEvent.getMobileNumber();
        this.errorMsg = cusMobNumRollbackedEvent.getErrorMsg();
    }

}
