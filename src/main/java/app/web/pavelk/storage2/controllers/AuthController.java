package app.web.pavelk.storage2.controllers;

import app.web.pavelk.storage2.dto.LoginRequestDto;
import app.web.pavelk.storage2.dto.LoginResponseDto;
import app.web.pavelk.storage2.services.AuthService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @NonNull LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

}
