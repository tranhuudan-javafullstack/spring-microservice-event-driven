package com.huudan.accounts.command.aggregate;

import com.huudan.accounts.command.CreateAccountCommand;
import com.huudan.accounts.command.DeleteAccountCommand;
import com.huudan.accounts.command.UpdateAccountCommand;
import com.huudan.accounts.command.event.AccountCreatedEvent;
import com.huudan.accounts.command.event.AccountDeletedEvent;
import com.huudan.accounts.command.event.AccountUpdatedEvent;
import com.huudan.common.command.RollbackAccntMobNumCommand;
import com.huudan.common.command.UpdateAccntMobileNumCommand;
import com.huudan.common.event.AccntMobNumRollbackedEvent;
import com.huudan.common.event.AccntMobileNumUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class AccountsAggregate {

    @AggregateIdentifier
    private Long accountNumber;
    private String mobileNumber;
    private String accountType;
    private String branchAddress;
    private boolean activeSw;
    private String errorMsg;

    public AccountsAggregate() {
    }

    @CommandHandler
    public AccountsAggregate(CreateAccountCommand createCommand) {
        AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent();
        BeanUtils.copyProperties(createCommand, accountCreatedEvent);
        AggregateLifecycle.apply(accountCreatedEvent);
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        this.accountNumber = accountCreatedEvent.getAccountNumber();
        this.mobileNumber = accountCreatedEvent.getMobileNumber();
        this.accountType = accountCreatedEvent.getAccountType();
        this.branchAddress = accountCreatedEvent.getBranchAddress();
        this.activeSw = accountCreatedEvent.isActiveSw();
    }

    @CommandHandler
    public void handle(UpdateAccountCommand updateCommand) {
        AccountUpdatedEvent accountUpdatedEvent = new AccountUpdatedEvent();
        BeanUtils.copyProperties(updateCommand, accountUpdatedEvent);
        AggregateLifecycle.apply(accountUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(AccountUpdatedEvent accountUpdatedEvent) {
        this.accountType = accountUpdatedEvent.getAccountType();
        this.branchAddress = accountUpdatedEvent.getBranchAddress();
    }

    @CommandHandler
    public void handle(DeleteAccountCommand deleteCommand) {
        AccountDeletedEvent accountDeletedEvent = new AccountDeletedEvent();
        BeanUtils.copyProperties(deleteCommand, accountDeletedEvent);
        AggregateLifecycle.apply(accountDeletedEvent);
    }

    @EventSourcingHandler
    public void on(AccountDeletedEvent accountDeletedEvent) {
        this.activeSw = accountDeletedEvent.isActiveSw();
    }

    @CommandHandler
    public void handle(UpdateAccntMobileNumCommand updateAccntMobileNumCommand) {
        AccntMobileNumUpdatedEvent accntMobileNumUpdatedEvent = new AccntMobileNumUpdatedEvent();
        BeanUtils.copyProperties(updateAccntMobileNumCommand, accntMobileNumUpdatedEvent);
        AggregateLifecycle.apply(accntMobileNumUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(AccntMobileNumUpdatedEvent accntMobileNumUpdatedEvent) {
        this.mobileNumber = accntMobileNumUpdatedEvent.getNewMobileNumber();
    }

    @CommandHandler
    public void handle(RollbackAccntMobNumCommand rollbackAccntMobNumCommand) {
        AccntMobNumRollbackedEvent accntMobNumRollbackedEvent = new AccntMobNumRollbackedEvent();
        BeanUtils.copyProperties(rollbackAccntMobNumCommand, accntMobNumRollbackedEvent);
        AggregateLifecycle.apply(accntMobNumRollbackedEvent);
    }

    @EventSourcingHandler
    public void on(AccntMobNumRollbackedEvent accntMobNumRollbackedEvent) {
        this.mobileNumber = accntMobNumRollbackedEvent.getMobileNumber();
        this.errorMsg = accntMobNumRollbackedEvent.getErrorMsg();
    }

}
