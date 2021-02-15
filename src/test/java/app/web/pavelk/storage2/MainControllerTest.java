package app.web.pavelk.storage2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Storage2.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void main1() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ng"));
    }

    @Test
    void homePage1() throws Exception {
        mockMvc.perform(get("/ng/login"))
                .andDo(print())
                .andExpect(forwardedUrl("/ng/index.html"));

        mockMvc.perform(get("/ng"))
                .andDo(print())
                .andExpect(forwardedUrl("/ng/index.html"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void adminPage1Right() throws Exception {
        mockMvc.perform(
                get("/admin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("name admin role [ROLE_ADMIN]"));

    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN", "USER"})
    void userPage1Right() throws Exception {
        mockMvc.perform(
                get("/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("name admin role [ROLE_ADMIN, ROLE_USER]"));
    }

}
