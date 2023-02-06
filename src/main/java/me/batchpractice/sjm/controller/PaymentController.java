package me.batchpractice.sjm.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.batchpractice.sjm.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    //구매하기버튼 눌렀을 때 session에 있는 사용자의 정보를 받아서 payment table에 결제 정보를 넣는다.
    @PostMapping("/payment")
    public String payment(String productId, HttpSession session) {
        paymentService.payment(Long.parseLong(productId), session);
        return "redirect:/";
    }

}
