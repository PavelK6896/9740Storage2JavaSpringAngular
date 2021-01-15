package app.web.pavelk.storage2.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MainService {

    public String homePage() {
        return "/ng/index.html";
    }

    public ResponseEntity<String> adminPage() {
        User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body("name " + userPrincipal.getUsername() + " role " + userPrincipal.getAuthorities());
    }

    public ResponseEntity<String> userPage() {
        User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body("name " + userPrincipal.getUsername() + " role " + userPrincipal.getAuthorities());
    }
}
