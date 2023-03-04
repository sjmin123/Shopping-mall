package me.batchpractice.sjm.config;

import lombok.RequiredArgsConstructor;
import me.batchpractice.sjm.service.LoginService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.httpBasic().disable(); // id,password 그대로 넘겨주는 필터. 끈다.
        // csrf
        http.csrf().disable(); // 위조 페이지 방지 필터.
        // remember-me
        http.rememberMe(); // 쿠키 더 오랫동안 기억하도록 하는 필터.
        // authorization
        http.authorizeHttpRequests()
                .requestMatchers("/", "/sign-up", "/signup-page", "/api/batch/mail").permitAll()
                .requestMatchers("/images/**", "/css/**").permitAll()//controller mapping 주소로 해야한다.
                .anyRequest().authenticated();
        http.formLogin()
                .loginPage("/")     //controller 에서 루프페이지는 login 페이지로 넘겨주기 때문에 루트페이지로 설정
                .failureUrl("/")    //fail 도 마찬가지
                .defaultSuccessUrl("/products")     //api 에 /products 요청이 있음.
                .usernameParameter("id")            // 아이디 파라미터명 설정. form 테그의 name 과 같아야 한다.
                .passwordParameter("password")            // 패스워드 파라미터명 설정. form 테그의 name 과 같아야 한다.
                .loginProcessingUrl("/login")            // 로그인 Form Action Url 과 같아야함(로그인의 action 이 여기로 매핑되서 자동으로 검사한다.)
                .failureHandler((request, response, exception) -> {
                    System.out.println("exception" + exception.getMessage());
                    response.sendRedirect("/");
                })
                .permitAll(); // 모두 허용
        http.logout() // 로그아웃 기능 작동함
                .logoutUrl("/logout") // 로그아웃 처리 URL, default: /logout, 원칙적으로 post 방식만 지원
                .logoutSuccessUrl("/") // 로그아웃 성공 후 이동페이지
                .deleteCookies("JSESSIONID", "remember-me"); // 로그아웃 후 쿠키 삭제

        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        // 정적 리소스 spring security 대상에서 제외
////        web.ignoring().antMatchers("/images/**", "/css/**"); // 아래 코드와 같은 코드입니다.
////        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//        return (web )->web.ignoring()
////                .requestMatchers("/images/**", "/css/**");
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }

    //암호화 방식 설정
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, LoginService loginService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(loginService)   // UserDetailsService 의 구현체 서비스를 반환.
                .passwordEncoder(bCryptPasswordEncoder) // 비밀번호가 bCrypt 가 아니라면 대조가 안됨.
                .and()
                .build();
    }

}
