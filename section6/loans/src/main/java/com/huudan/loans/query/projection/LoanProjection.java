package com.huudan.loans.query.projection;

import com.huudan.common.event.LoanMobileNumUpdatedEvent;
import com.huudan.loans.command.event.LoanCreatedEvent;
import com.huudan.loans.command.event.LoanDeletedEvent;
import com.huudan.loans.command.event.LoanUpdatedEvent;
import com.huudan.loans.entity.Loans;
import com.huudan.loans.service.ILoansService;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("loan-group")
public class LoanProjection {

    private final ILoansService iLoansService;

    @EventHandler
    public void on(LoanCreatedEvent event) {
        Loans loanEntity = new Loans();
        BeanUtils.copyProperties(event, loanEntity);
        iLoansService.createLoan(loanEntity);
    }

    @EventHandler
    public void on(LoanUpdatedEvent event) {
        iLoansService.updateLoan(event);
    }

    @EventHandler
    public void on(LoanDeletedEvent event) {
        iLoansService.deleteLoan(event.getLoanNumber());
    }

    @EventHandler
    public void on(LoanMobileNumUpdatedEvent event) {
        iLoansService.updateMobileNumber(event.getMobileNumber(), event.getNewMobileNumber());
    }

}
