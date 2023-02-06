package me.batchpractice.sjm.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import me.batchpractice.sjm.dto.DailyPaymentBillStuff;
import me.batchpractice.sjm.dto.request.DailyPaymentBillRequest;
import me.batchpractice.sjm.entity.User;
import me.batchpractice.sjm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService {

    final UserRepository userRepository;
    @Autowired
    private final JavaMailSender emailSender;

    //이메일보내기 비즈니스로직
    public void sendDailyPaymentBillSend(List<DailyPaymentBillRequest> req) {
        req.forEach(r -> {
            Optional<User> user = userRepository.findById(r.getId());
            sendDailyPaymentBillMail(
                    DailyPaymentBillStuff.builder()
                            .daily_payment(r.getDailyPayment())
                            .toEmail(user.get().getEmail())
                            .userId(user.get().getUserId())
                            .build()
            );
        });
    }

    //이메일보내기 비즈니스로직중에 실제로 메일 보내는 로직
    public void sendDailyPaymentBillMail(DailyPaymentBillStuff dailyPaymentBillStuff) {
        final MimeMessagePreparator preparator = (MimeMessage message) -> {
            final MimeMessageHelper helper = new MimeMessageHelper(message);
            //누구에게
            helper.setTo(dailyPaymentBillStuff.getToEmail());
            //제목
            helper.setSubject("오늘 하루 결제 총 금액");
            //내용(process 는 view 화면을 보내는 것.)
            helper.setText(dailyPaymentBillStuff+" 고객님께서 구매하신 오늘의 결제 금액은 총"
                    + dailyPaymentBillStuff.getDaily_payment() + "입니다."
            );
        };
        emailSender.send(preparator);
    }

}