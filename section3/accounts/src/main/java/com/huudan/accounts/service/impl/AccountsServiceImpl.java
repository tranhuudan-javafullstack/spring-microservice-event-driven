package com.huudan.accounts.service.impl;

import com.huudan.accounts.command.event.AccountUpdatedEvent;
import com.huudan.accounts.constants.AccountsConstants;
import com.huudan.accounts.dto.AccountsDto;
import com.huudan.accounts.entity.Accounts;
import com.huudan.accounts.exception.AccountAlreadyExistsException;
import com.huudan.accounts.exception.ResourceNotFoundException;
import com.huudan.accounts.mapper.AccountsMapper;
import com.huudan.accounts.repository.AccountsRepository;
import com.huudan.accounts.service.IAccountsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountsServiceImpl implements IAccountsService {

    private final AccountsRepository accountsRepository;

    public AccountsServiceImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    /**
     * @param account - Accounts
     */
    @Override
    public void createAccount(Accounts account) {
        Optional<Accounts> optionalAccounts = accountsRepository.findByMobileNumberAndActiveSw(account.getMobileNumber(),
                AccountsConstants.ACTIVE_SW);
        if (optionalAccounts.isPresent()) {
            throw new AccountAlreadyExistsException("Account already registered with given mobileNumber " + account.getMobileNumber());
        }
        accountsRepository.save(account);
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    @Override
    public AccountsDto fetchAccount(String mobileNumber) {
        Accounts account = accountsRepository.findByMobileNumberAndActiveSw(mobileNumber, AccountsConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "mobileNumber", mobileNumber)
                );
        return AccountsMapper.mapToAccountsDto(account, new AccountsDto());
    }

    /**
     * @param event - AccountUpdatedEvent Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(AccountUpdatedEvent event) {
        Accounts account = accountsRepository.findByMobileNumberAndActiveSw(event.getMobileNumber(),
                AccountsConstants.ACTIVE_SW).orElseThrow(() -> new ResourceNotFoundException("Account", "mobileNumber",
                event.getMobileNumber()));
        AccountsMapper.mapEventToAccount(event, account);
        accountsRepository.save(account);
        return true;
    }

    /**
     * @param accountNumber - Input Account Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(Long accountNumber) {
        Accounts account = accountsRepository.findById(accountNumber).orElseThrow(
                () -> new ResourceNotFoundException("Account", "accountNumber", accountNumber.toString())
        );
        account.setActiveSw(AccountsConstants.IN_ACTIVE_SW);
        accountsRepository.save(account);
        return true;
    }


}
