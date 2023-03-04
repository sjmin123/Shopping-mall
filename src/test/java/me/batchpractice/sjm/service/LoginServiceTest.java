package me.batchpractice.sjm.service;

import jakarta.servlet.http.HttpSession;
import me.batchpractice.sjm.dto.request.LoginRequestDto;
import me.batchpractice.sjm.dto.request.UserRequestDto;
import me.batchpractice.sjm.entity.User;
import me.batchpractice.sjm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

import java.util.Optional;

import static me.batchpractice.sjm.service.LoginService.LOGIN_SESSION_KEY;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

//주의해야 할 것은 서비스테스트는 서비스에 있는 로직만 테스트 해야 한다.
//그러므로 리포지토리를 모킹하는 것이다.

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginService loginService;


    //로그인 테스트
    //성공
//    @Test
//    void 로그인서비스테스트(){
//        //given
//        HttpSession session=new MockHttpSession();
//        final Optional<User> user = Optional.of(User.builder()
//                .userId("asdf")
//                .password("asdf")
//                .build());
//        given(userRepository.findByUserId("asdf")).willReturn(user);
//
//        //when
//        //todo model에 any() 들어감.
//        boolean result=loginService.login(new LoginRequestDto("asdf","asdf"));
//
//        //then
//        assertThat(result).isTrue();
//        //세션이 올바르게 들어갔는지 검사.
//        assertThat(session.getAttribute(LOGIN_SESSION_KEY)).isEqualTo(user.get().getUserId());
//    }
//
//    //실패_아이디가틀림
//    @Test
//    void 로그인서비스테스트_실패1(){
//        //given
//        HttpSession session=new MockHttpSession();
//        given(userRepository.findByUserId(any())).willReturn(Optional.empty());
//
//        //when
//        boolean result=loginService.login(new LoginRequestDto("asdff","asdf"));
//
//        //then
//        assertThat(result).isFalse();
//        assertThat(session.getAttribute(LOGIN_SESSION_KEY)).isNull();
//
//    }
//
//    //실패_비밀번호가 틀림
//    @Test
//    void 로그인서비스테스트_실패2(){
//        //given
//        HttpSession session=new MockHttpSession();
//        //응답
//        final Optional<User> user = Optional.of(User.builder()
//                .userId("asdf")
//                .password("asdf")
//                .build());
//        given(userRepository.findByUserId("asdf")).willReturn(user);
//
//        //when
//        boolean result=loginService.login(new LoginRequestDto("asdf","asdfjhjvjf"));
//
//        //then
//        assertThat(result).isFalse();
//        assertThat(session.getAttribute(LOGIN_SESSION_KEY)).isNull();
//    }
//
//    //로그아웃 서비스 테스트(세션이 없어지는지만 확인.)
//    //성공
//    //session 테스트 (session mocking)
//    @Test
//    void 로그아웃테스트_성공(){
//
//    //given
//        HttpSession session=new MockHttpSession();
//        session.setAttribute(LOGIN_SESSION_KEY, "테스트");
//
//    //when
//        loginService.logout(session);
//
//    //then
//        assertThat(session.getAttribute(LOGIN_SESSION_KEY)).isNull();
//
//    }
//
//    //실패
//    //예외 처리 테스트
//    //session 테스트 (session mocking)
//    @Test
//    void 로그아웃테스트_실패_이미세션에없음(){
//
//        //given
//        HttpSession session=new MockHttpSession();
//
//        //when
////        loginService.logout(session);
////        필요 없음. assertThrows하면서 실행하기 때문에 when&then이다.
//
//        //when&then
//        Throwable exception=assertThrows(RuntimeException.class,()-> loginService.logout(session));
//        assertThat(exception.getMessage()).isEqualTo("this is logout exception");
//        //todo 예외 클래스 만들어서 던지기(간단하게) 참고. finalproject 22번동영상.
//
//    }

    //회원가입 테스트
    //성공
    @Test
    void 회원가입테스트_성공(){
        //given
        final User user = User.builder()
                .userId("asdf")
                .password("asdf")
                .email("sjsjsj@naver.com")
                .build();
        given(userRepository.save(any())).willReturn(user);
        //save(user) 대신에 save(any())로 해야지 오류가 없다.

        //when
        UserRequestDto userRequestDto = new UserRequestDto("asdf","asdf","sjsjsj@naver.com");
        String userIdResult=loginService.signUp(userRequestDto);

        //then
        //user 객체 전체를 비교하진 못하겠지?
        assertThat(userIdResult).isEqualTo(user.getUserId());
    }

    //실패
    @Test
    void 회원가입테스트_실패_아이디가빈칸(){
        //이미 컨트롤러 계층에서 처리할 것이기 때문에 (@Valid 로) 여기선 따로 하지 않을 것임
    }

    @Test
    void 회원가입테스트_실패_비밀번호가빈칸(){
        //이미 컨트롤러 계층에서 처리할 것이기 때문에 (@Valid 로) 여기선 따로 하지 않을 것임
    }

}