package shop.mtcoding.restdocapp.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest // 통합 테스트시에 모든 클래스를 메모리에 띄우기
public class UserControllerTest {

    @Autowired
        private MockMvc mvc;
    
    @Test
    public void join_test() throws Exception{
        
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setUsername("cos");
        requestDTO.setPassword("1234");
        requestDTO.setEmail("cos@nate.com");
        ObjectMapper om = new ObjectMapper();
        String requestBody = om.writeValueAsString(requestDTO); // dto를 json으로 변경해줌
        System.out.println("=========================");
        System.out.println(requestBody);
        System.out.println("=========================");

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        // 상태검증 (jsonPath : json상태에서 값을 찾아줌)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("cos"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("cos@nate.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty());
    }

    @Test
    public void userInfo_test(){}
}
