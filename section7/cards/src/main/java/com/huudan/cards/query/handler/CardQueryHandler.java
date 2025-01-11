package com.huudan.cards.query.handler;

import com.huudan.cards.dto.CardsDto;
import com.huudan.cards.query.FindCardQuery;
import com.huudan.cards.service.ICardsService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardQueryHandler {

    private final ICardsService iCardsService;

    @QueryHandler
    public CardsDto findCard(FindCardQuery query) {
        return iCardsService.fetchCard(query.getMobileNumber());
    }

}
