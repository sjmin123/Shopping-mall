package me.batchpractice.sjm.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.batchpractice.sjm.dto.request.UserRequestDto;
import me.batchpractice.sjm.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    //기본페이지
    @GetMapping("/")
    public String index() {
        return "/login-page";
    }

    //로그인
    //spring security 로 로그인을 구현했기 때문에 필요가 없다.
//    @PostMapping("/login")
//    public String login(@ModelAttribute("userRequestDto") LoginRequestDto loginRequestDto, HttpSession session) {
//        if (loginService.login(loginRequestDto, session)) {
//            System.out.println("테스트1");
//            return "redirect:/products";
//        }
//        return "redirect:/";
//    }

    //로그아웃
//    @GetMapping("/logout")
//    public ResponseEntity<Void> logout(HttpSession session) {
//        loginService.logout(session);
//        return ResponseEntity.ok().build();
//    }

    //회원가입
    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute UserRequestDto userRequestDto) {
        loginService.signUp(userRequestDto);
        return "/login-page";
    }

    //회원가입페이지이동
    @GetMapping("/signup-page")
    public String signUpPage() {
        return "/signup-page";
    }

}
