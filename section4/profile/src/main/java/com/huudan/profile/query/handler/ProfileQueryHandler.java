package com.huudan.profile.query.handler;

import com.huudan.profile.dto.ProfileDto;
import com.huudan.profile.query.FindProfileQuery;
import com.huudan.profile.service.IProfileService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileQueryHandler {

    private final IProfileService iProfileService;

    @QueryHandler
    public ProfileDto findProfile(FindProfileQuery findProfileQuery) {
        return iProfileService.fetchProfile(findProfileQuery.getMobileNumber());
    }

}
