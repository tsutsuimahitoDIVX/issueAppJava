package in.techcamp.issueAppJava;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSuccessfulLogin() throws Exception {
        mockMvc.perform(post("/login") // ①
               .contentType(MediaType.APPLICATION_FORM_URLENCODED) // ②
               .param("username", "techcamp") //③
               .param("password", "")) //　④
               .andExpect(status().is3xxRedirection()) // ⑤
               .andExpect(redirectedUrl("/")); // ⑥
        }
}
