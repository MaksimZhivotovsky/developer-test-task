package max.example.controllers;

import lombok.RequiredArgsConstructor;
import max.example.entities.User;
import max.example.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final UserRepository userRepository;

    @GetMapping("/unsecured")
    public String unsecuredData() {
        return "Unsecured data";
    }

    @GetMapping("/secured")
    public String securedData() {
        return "Secured data";
    }

    @GetMapping("/admin")
    public String adminData() {
        return "Admin data";
    }

    @GetMapping("/info")
    public Long userData(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();

        return user.getUserId();
    }
}
