package app.web.pavelk.storage2;

import app.web.pavelk.storage2.dto.LoginRequestDto;
import app.web.pavelk.storage2.entities.Status;
import app.web.pavelk.storage2.entities.User;
import app.web.pavelk.storage2.repositories.RoleRepository;
import app.web.pavelk.storage2.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Storage2.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    private void ClearBase() {
        userRepository.deleteAll();
    }

    String username = "Fsdfsadf";
    String password = "asdf!#@!4f324dfsafsdf";

    @Test
    void login1Right() throws Exception {
        User user = User.builder().password(passwordEncoder.encode(password)).username(username)
                .status(Status.ACTIVE).email("e").roles(new ArrayList<>(roleRepository.findAll())).build();
        userRepository.save(user);

        LoginRequestDto loginRequestDto = LoginRequestDto.builder().username(username).password(password).build();

        mockMvc.perform(post("/login")
                .content(objectMapper.writeValueAsString(loginRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(username)))
                .andExpect(jsonPath("$.authenticationToken").exists())
                .andExpect(jsonPath("$.expiresAt").exists());
    }

    @Test
    void login2Wrong() throws Exception {
        LoginRequestDto loginRequestDto = LoginRequestDto.builder().username(username).password(password).build();

        mockMvc.perform(post("/login")
                .content(objectMapper.writeValueAsString(loginRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(403))
                .andExpect(content().string("Bad credentials"));
    }


    @Test
    void logout1Right() throws Exception {
        mockMvc.perform(post("/logout1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
