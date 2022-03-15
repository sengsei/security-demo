package de.neuefische.securitydemo.controller;

import de.neuefische.securitydemo.UserDocument;
import de.neuefische.securitydemo.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class GreetingController {

    private final UserService userService;

    @GetMapping
    public String greet(Principal principal){
        UserDocument user = userService.findByEmail(principal.getName()).orElseThrow();
        return "Moin " + user.getEmail() + user.getPassword();
    }
}
