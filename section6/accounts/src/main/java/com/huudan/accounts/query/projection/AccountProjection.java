package com.huudan.accounts.query.projection;

import com.huudan.accounts.command.event.AccountCreatedEvent;
import com.huudan.accounts.command.event.AccountDeletedEvent;
import com.huudan.accounts.command.event.AccountUpdatedEvent;
import com.huudan.accounts.entity.Accounts;
import com.huudan.accounts.service.IAccountsService;
import com.huudan.common.event.AccntMobNumRollbackedEvent;
import com.huudan.common.event.AccntMobileNumUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("account-group")
public class AccountProjection {

    private final IAccountsService iAccountsService;

    @EventHandler
    public void on(AccountCreatedEvent event) {
        Accounts accountEntity = new Accounts();
        BeanUtils.copyProperties(event, accountEntity);
        iAccountsService.createAccount(accountEntity);
    }

    @EventHandler
    public void on(AccountUpdatedEvent event) {
        iAccountsService.updateAccount(event);
    }

    @EventHandler
    public void on(AccountDeletedEvent event) {
        iAccountsService.deleteAccount(event.getAccountNumber());
    }

    @EventHandler
    public void on(AccntMobileNumUpdatedEvent accntMobileNumUpdatedEvent) {
        iAccountsService.updateMobileNumber(accntMobileNumUpdatedEvent.getMobileNumber(), accntMobileNumUpdatedEvent.getNewMobileNumber());
    }

    @EventHandler
    public void on(AccntMobNumRollbackedEvent accntMobNumRollbackedEvent) {
        iAccountsService.updateMobileNumber(accntMobNumRollbackedEvent.getNewMobileNumber(), accntMobNumRollbackedEvent.getMobileNumber());
    }

}