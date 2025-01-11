package com.huudan.profile.service.impl;

import com.huudan.common.event.AccountDataChangedEvent;
import com.huudan.common.event.CardDataChangedEvent;
import com.huudan.common.event.CustomerDataChangedEvent;
import com.huudan.common.event.LoanDataChangedEvent;
import com.huudan.profile.constants.ProfileConstants;
import com.huudan.profile.dto.ProfileDto;
import com.huudan.profile.entity.Profile;
import com.huudan.profile.exception.ResourceNotFoundException;
import com.huudan.profile.mapper.ProfileMapper;
import com.huudan.profile.repository.ProfileRepository;
import com.huudan.profile.service.IProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements IProfileService {

    private ProfileRepository profileRepository;

    @Override
    public void handleCustomerDataChangedEvent(CustomerDataChangedEvent customerDataChangedEvent) {
        Profile profile =profileRepository.findByMobileNumberAndActiveSw(customerDataChangedEvent.getMobileNumber(),
                        ProfileConstants.ACTIVE_SW).orElseGet(Profile::new);
        profile.setMobileNumber(customerDataChangedEvent.getMobileNumber());
        if (customerDataChangedEvent.getName() != null) {
            profile.setName(customerDataChangedEvent.getName());
        }
        profile.setActiveSw(customerDataChangedEvent.isActiveSw());
        profileRepository.save(profile);
    }

    @Override
    public void handleAccountDataChangedEvent(AccountDataChangedEvent accountDataChangedEvent) {
        Profile profile = profileRepository.findByMobileNumberAndActiveSw(accountDataChangedEvent.getMobileNumber(),
                        ProfileConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "mobileNumber", accountDataChangedEvent.getMobileNumber()));
        profile.setAccountNumber(accountDataChangedEvent.getAccountNumber());
        profileRepository.save(profile);
    }

    @Override
    public void handleLoanDataChangedEvent(LoanDataChangedEvent loanDataChangedEvent) {
        Profile profile = profileRepository.findByMobileNumberAndActiveSw(loanDataChangedEvent.getMobileNumber(),
                        ProfileConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "mobileNumber", loanDataChangedEvent.getMobileNumber()));
        profile.setLoanNumber(loanDataChangedEvent.getLoanNumber());
        profileRepository.save(profile);
    }

    @Override
    public void handleCardDataChangedEvent(CardDataChangedEvent customerDataChangedEvent) {
        Profile profile = profileRepository.findByMobileNumberAndActiveSw(customerDataChangedEvent.getMobileNumber(),
                        ProfileConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "mobileNumber", customerDataChangedEvent.getMobileNumber()));
        profile.setCardNumber(customerDataChangedEvent.getCardNumber());
        profileRepository.save(profile);
    }

    @Override
    public ProfileDto fetchProfile(String mobileNumber) {
        Profile profile = profileRepository.findByMobileNumberAndActiveSw(mobileNumber, true).orElseThrow(
                () -> new ResourceNotFoundException("Profile", "mobileNumber", mobileNumber)
        );
        ProfileDto profileDto = ProfileMapper.mapToProfileDto(profile, new ProfileDto());
        return profileDto;
    }

}
