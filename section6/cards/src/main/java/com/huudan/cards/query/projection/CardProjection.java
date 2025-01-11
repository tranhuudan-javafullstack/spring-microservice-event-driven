package com.huudan.cards.query.projection;

import com.huudan.cards.command.event.CardCreatedEvent;
import com.huudan.cards.command.event.CardDeletedEvent;
import com.huudan.cards.command.event.CardUpdatedEvent;
import com.huudan.cards.entity.Cards;
import com.huudan.cards.service.ICardsService;
import com.huudan.common.event.CardMobNumRollbackedEvent;
import com.huudan.common.event.CardMobileNumUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("card-group")
public class CardProjection {

    private final ICardsService iCardsService;

    @EventHandler
    public void on(CardCreatedEvent event) {
        Cards cardEntity = new Cards();
        BeanUtils.copyProperties(event, cardEntity);
        iCardsService.createCard(cardEntity);
    }

    @EventHandler
    public void on(CardUpdatedEvent event) {
        iCardsService.updateCard(event);
    }

    @EventHandler
    public void on(CardDeletedEvent event) {
        iCardsService.deleteCard(event.getCardNumber());
    }

    @EventHandler
    public void on(CardMobileNumUpdatedEvent event) {
        iCardsService.updateMobileNumber(event.getMobileNumber(), event.getNewMobileNumber());
    }

    @EventHandler
    public void on(CardMobNumRollbackedEvent event) {
        iCardsService.updateMobileNumber(event.getNewMobileNumber(), event.getMobileNumber());
    }

}
