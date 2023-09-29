package in.techcamp.issueAppJava;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IssueControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testShowIssues() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")) //　①
               .andExpect(status().isOk()) //②
               .andExpect(MockMvcResultMatchers.view().name("index")) // ③
               .andExpect(MockMvcResultMatchers.model().attributeExists("issueList")); // ④
    }


//    @Test
//    public void testSuccessfulLogin() throws Exception {
//        mockMvc.perform(post("/login")
//               .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//               .param("username", "techcamp")
//               .param("password", "techcamp"))
//               .andExpect(status().is3xxRedirection())
//               .andExpect(redirectedUrl("/"));
//        }
}
