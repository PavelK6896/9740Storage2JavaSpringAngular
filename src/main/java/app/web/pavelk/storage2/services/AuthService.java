package app.web.pavelk.storage2.services;


import app.web.pavelk.storage2.dto.LoginRequestDto;
import app.web.pavelk.storage2.dto.LoginResponseDto;
import app.web.pavelk.storage2.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.OK;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        log.info("login");
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        LoginResponseDto loginResponseDto = jwtTokenProvider.generateToken(authenticate);

        return ResponseEntity.status(OK).body(loginResponseDto);
    }

    public ResponseEntity<String> logout() {
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!");
    }
}
