package me.batchpractice.sjm.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.batchpractice.sjm.entity.PayInfo;
import me.batchpractice.sjm.entity.Product;
import me.batchpractice.sjm.repository.PayInfoRepository;
import me.batchpractice.sjm.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

import static me.batchpractice.sjm.service.LoginService.LOGIN_SESSION_KEY;

@Service
@RequiredArgsConstructor
public class PaymentService {

    final PayInfoRepository payInfoRepository;
    final ProductRepository productRepository;

    public final static String PRODUCTS_SESSION_KEY = "productList";

    //구매하기
    @Transactional
    public void payment(Long productId, HttpSession session) {
        PayInfo payInfo = PayInfo.builder()
                .payUser((Long) session.getAttribute(LOGIN_SESSION_KEY))
                .productId(productId)
                .paymentTime(LocalDateTime.now())
                .build();

        payInfoRepository.save(payInfo);
    }

    //모든 상품 불러오기
    public void getAllProducts(Model model) {
        List<Product> productList = productRepository.findAll();
        model.addAttribute(PRODUCTS_SESSION_KEY, productList);
    }
}
