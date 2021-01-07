package app.web.pavelk.storage2.services;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.security.Principal;


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
