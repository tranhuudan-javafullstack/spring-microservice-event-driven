package com.huudan.profile.service;

import com.huudan.common.event.AccountDataChangedEvent;
import com.huudan.common.event.CardDataChangedEvent;
import com.huudan.common.event.CustomerDataChangedEvent;
import com.huudan.common.event.LoanDataChangedEvent;
import com.huudan.profile.dto.ProfileDto;

public interface IProfileService {

    /**
     * @param customerDataChangedEvent - CustomerDataChangedEvent Object
     */
    void handleCustomerDataChangedEvent(CustomerDataChangedEvent customerDataChangedEvent);

    /**
     * @param accountDataChangedEvent - AccountDataChangedEvent Object
     */
    void handleAccountDataChangedEvent(AccountDataChangedEvent accountDataChangedEvent);

    /**
     * @param loanDataChangedEvent - LoanDataChangedEvent Object
     */
    void handleLoanDataChangedEvent(LoanDataChangedEvent loanDataChangedEvent);

    /**
     * @param customerDataChangedEvent - CardDataChangedEvent Object
     */
    void handleCardDataChangedEvent(CardDataChangedEvent customerDataChangedEvent);

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Profile Details based on a given mobileNumber
     */
    ProfileDto fetchProfile(String mobileNumber);
}
