package me.batchpractice.sjm.controller;

import lombok.RequiredArgsConstructor;
import me.batchpractice.sjm.dto.request.DailyPaymentBillRequest;
import me.batchpractice.sjm.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MailBatchController {

    private final EmailService emailService;

    @PostMapping("/api/batch/mail")
    public void sendMail(@RequestBody List<DailyPaymentBillRequest> req) {
        emailService.sendDailyPaymentBillSend(req);
    }

}
