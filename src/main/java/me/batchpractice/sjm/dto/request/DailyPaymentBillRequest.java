package me.batchpractice.sjm.dto.request;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class DailyPaymentBillRequest {

    private Long id;
    private Long dailyPayment=0L;

}