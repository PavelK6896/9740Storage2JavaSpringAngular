package app.web.pavelk.storage2.controllers;


import app.web.pavelk.storage2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class MainController {

    private UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage(Principal principal) {

        if(principal == null){
            return "redirect:/login";
        }
        return "../static/index";

    }

    @GetMapping("/admin")
    @ResponseBody
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/user")
    @ResponseBody
    public String user() {
        return "user";
    }

}
