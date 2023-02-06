package me.batchpractice.sjm.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DailyPaymentBillStuff {
    //메일 보낼때 필요한 정보

    private final String toEmail;
    private final Long daily_payment;
    private final String userId;

}
