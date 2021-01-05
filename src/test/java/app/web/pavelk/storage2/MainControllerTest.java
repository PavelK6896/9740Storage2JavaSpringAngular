package app.web.pavelk.storage2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = Storage2.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void homePage1() throws Exception {
        mockMvc.perform(
                get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("index.html"));

    }

    @Test
    @WithAnonymousUser
    public void homePage2() throws Exception {

        mockMvc.perform(
                get("/"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void adminPage1Right() throws Exception {
        mockMvc.perform(
                get("/admin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("name admin role [ROLE_ADMIN]"));

    }

    @Test
    public void userPage1Right() throws Exception {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        mockMvc.perform(
                get("/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("name admin role [ROLE_ADMIN, ROLE_USER]"));
    }

}
