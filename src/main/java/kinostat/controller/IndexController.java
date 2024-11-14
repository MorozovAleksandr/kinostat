package kinostat.controller;

import kinostat.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(@AuthenticationPrincipal User user) {
        System.out.println(user);
        return "index";
    }
}
