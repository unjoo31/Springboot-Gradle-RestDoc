package shop.mtcoding.restdocapp.user;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.restdocapp.MyWithRestDoc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// 클래스 상단에 @AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080) 어노테이션 추가
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@SpringBootTest // 통합 테스트시에 모든 클래스를 메모리에 띄우기
public class UserControllerTest extends MyWithRestDoc{ // MyWithRestDoc 파일을 상속해서 사용

    
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
        // mockMvc와 document를 부모에게 물려받아서 사용
        ResultActions resultActions = mockMvc.perform(
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.username").value("cos"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.password").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.email").value("cos@nate.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty())
                // AbstractControllerTest 파일에서 adoc파일 생성 위치를 자동화함.
                .andDo(MockMvcResultHandlers.print())
                .andDo(document);
    }

    @Test
    public void user_info_test() throws Exception {
        // given
        int id = 1;

        // when
        // mockMvc와 document를 부모에게 물려받아서 사용
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/users/"+id)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.username").value("ssar"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.password").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.email").value("ssar@nate.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty())
                // AbstractControllerTest 파일에서 adoc파일 생성 위치를 자동화함.
                .andDo(MockMvcResultHandlers.print()) 
                .andDo(document); 
    }
}
