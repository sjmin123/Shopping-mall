package me.batchpractice.sjm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import me.batchpractice.sjm.dto.request.LoginRequestDto;
import me.batchpractice.sjm.dto.request.UserRequestDto;
import me.batchpractice.sjm.service.LoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@AutoConfigureMockMvc
@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private LoginService loginService;

    //로그인
    //성공
    @Test
    void 로그인_성공() throws Exception {
        //given
        HttpSession session=new MockHttpSession();
        Map<String,String> input=new HashMap<>();
        input.put("id","asdf");
        input.put("password","asdf");
        input.put("email","sjsjsj@naver.com");
        //todo model 을 any()로 했는데 제대로 model도 테스트하기.
        given(loginService.login(new LoginRequestDto("asdf","asdf"),session,any() )).willReturn(true);

        //when&then
        mvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        //json 형식으로 데이터를 보낸다고 명시
                        .content(objectMapper.writeValueAsString(input)))
                        //Map으로 만든 input을 json형식의 String으로 만들기 위해 objectMapper를 사용
                .andExpect(status().isOk())
                .andExpect(content().string("/main-page"));
    }
    //실패
    @Test
    void 로그인_실패() throws Exception {
        //givene
        HttpSession session=new MockHttpSession();
        Map<String,String> input=new HashMap<>();
        input.put("id","asdf");
        input.put("password","asdffff"); //잘못 입력.
        given(loginService.login(new LoginRequestDto("asdf","asdffff"),session, any())).willReturn(false);

        //when&then
        mvc.perform(
                        MockMvcRequestBuilders.post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                //json 형식으로 데이터를 보낸다고 명시
                                .content(objectMapper.writeValueAsString(input)))
                //Map으로 만든 input을 json형식의 String으로 만들기 위해 objectMapper를 사용
                .andExpect(status().isOk())
                .andExpect(content().string("redirect:/"));

    }

    @Test
    void logout() {
        //request-void
        //response-void
        //내용-성공시에는 로그인 페이지리턴
        //실패시네는 화면 그대로 리턴
    }

    @Test
    void signUp() {
        //request-UserRequestDto
        //response-
        //내용 성공시에는 로그인 화면 리턴
        //실패시에는 회원가입 화면 리턴
    }
}