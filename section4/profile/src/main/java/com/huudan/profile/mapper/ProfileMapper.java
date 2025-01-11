package com.huudan.profile.mapper;

import com.huudan.profile.dto.ProfileDto;
import com.huudan.profile.entity.Profile;

public class ProfileMapper {


    public static ProfileDto mapToProfileDto(Profile profile, ProfileDto profileDto) {
        profileDto.setName(profile.getName());
        profileDto.setMobileNumber(profile.getMobileNumber());
        profileDto.setAccountNumber(profile.getAccountNumber());
        profileDto.setLoanNumber(profile.getLoanNumber());
        profileDto.setCardNumber(profile.getCardNumber());
        return profileDto;
    }

}
