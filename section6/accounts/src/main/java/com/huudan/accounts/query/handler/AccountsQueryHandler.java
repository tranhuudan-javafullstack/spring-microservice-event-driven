package com.huudan.accounts.query.handler;

import com.huudan.accounts.dto.AccountsDto;
import com.huudan.accounts.query.FindAccountQuery;
import com.huudan.accounts.service.IAccountsService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountsQueryHandler {

    private final IAccountsService iAccountsService;

    @QueryHandler
    public AccountsDto findAccount(FindAccountQuery query) {
        AccountsDto account = iAccountsService.fetchAccount(query.getMobileNumber());
        return account;
    }

}