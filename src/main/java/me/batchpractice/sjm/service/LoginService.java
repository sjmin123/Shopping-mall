package me.batchpractice.sjm.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.batchpractice.sjm.dto.request.LoginRequestDto;
import me.batchpractice.sjm.dto.request.UserRequestDto;
import me.batchpractice.sjm.entity.User;
import me.batchpractice.sjm.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LoginService {

    final UserRepository userRepository;
    public final static String LOGIN_SESSION_KEY = "userId";
    //로그인 성공시에 모든 상품을 한번에 불러오기 위해 login 메소드에 넣을 것임. 그래서 PaymentService 임포트.
    final PaymentService paymentService;

    //로그인
    public boolean login(LoginRequestDto loginRequestDto, HttpSession session, Model model) {
        final Optional<User> user = userRepository.findByUserId(loginRequestDto.getId());
        if (user.isPresent()) {
            if (loginRequestDto.getPassword().equals(user.get().getPassword())) {
                session.setAttribute(LOGIN_SESSION_KEY, user.get().getId());
                //productList 불러오는 서비스로직메소드
                paymentService.getAllProducts(model);
                return true;
            }
            return false;
        }
        return false;
    }

    //로그아웃
    public void logout(HttpSession session) {
        if (session.getAttribute(LOGIN_SESSION_KEY) == null) {
            throw new RuntimeException("this is logout exception");
        }
        session.removeAttribute(LOGIN_SESSION_KEY);
    }

    //회원가입
    public String signUp(UserRequestDto userRequestDto) {
        User user = userRepository.save(User.builder()
                .userId(userRequestDto.getId())
                .password(userRequestDto.getPassword())
                .email(userRequestDto.getEmail())
                .build());
        return user.getUserId();
    }
}
