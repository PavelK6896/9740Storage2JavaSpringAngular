package app.web.pavelk.storage2.controllers;


import app.web.pavelk.storage2.services.MainService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class MainController {
    private final MainService mainService;

    @GetMapping("/")
    public String homePage(Principal principal) {
        return mainService.homePage(principal);
    }

    @GetMapping("/admin")
    @ResponseBody
    public ResponseEntity<String> adminPage(@NonNull Principal principal) {
        return mainService.adminPage(principal);
    }

    @GetMapping("/user")
    @ResponseBody
    public ResponseEntity<String> userPage(@NonNull Principal principal) {
        return mainService.userPage(principal);
    }

}
