package com.huudan.loans.query.handler;

import com.huudan.loans.dto.LoansDto;
import com.huudan.loans.query.FindLoanQuery;
import com.huudan.loans.service.ILoansService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanQueryHandler {

    private final ILoansService iLoansService;

    @QueryHandler
    public LoansDto findLoan(FindLoanQuery query) {
        return iLoansService.fetchLoan(query.getMobileNumber());
    }

}
