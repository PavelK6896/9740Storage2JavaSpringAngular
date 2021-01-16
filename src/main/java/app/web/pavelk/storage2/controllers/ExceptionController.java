package app.web.pavelk.storage2.controllers;


import app.web.pavelk.storage2.exceptions.JwtValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;


@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> authenticationException(Exception e) {
        SecurityContextHolder.clearContext();
        log.error(e.getMessage() + "JWT token is expired or invalid");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(JwtValidException.class)
    public ResponseEntity<String> jwtValidException(Exception e) {
        log.error(e.getMessage() + "Token is invalid:!");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentException(Exception e) {
        log.error(e.getMessage() + " Ошибка");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> badCredentialsException(Exception e) {
        log.error(e.getMessage() + " Неверный логин или пароль");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> usernameNotFoundException(Exception e) {
        log.error(e.getMessage() + " пользователь не найден");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> nullPointerException(Exception e) {
        log.error(e.getMessage() + " данные не найдены");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidException(Exception e) {
        log.error(e.getMessage() + " ошибка параметры");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> iOException(Exception e) {
        log.error(e.getMessage() + " ошибка записи файла");
        return ResponseEntity.status(HttpStatus.GONE).body(e.getMessage());
    }
}
