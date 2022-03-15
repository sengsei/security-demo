package de.neuefische.securitydemo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminGreetingController {

    private final UserService userService;

    @GetMapping
    public String greet(Principal principal){
        UserDocument user = userService.findByEmail(principal.getName()).orElseThrow();
        return "Moin " + user.getEmail();
    }
}
