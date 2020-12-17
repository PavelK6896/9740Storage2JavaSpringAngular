package app.web.pavelk.storage2.services;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class MainService {

    public String homePage(Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        return "../static/index";
    }

    public ResponseEntity<String> adminPage(Principal principal) {
        return ResponseEntity.ok().body("admin");
    }

    public ResponseEntity<String> userPage(Principal principal) {
        return ResponseEntity.ok().body("user");
    }
}
