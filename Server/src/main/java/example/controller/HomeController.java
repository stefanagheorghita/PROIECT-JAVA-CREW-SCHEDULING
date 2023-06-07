package example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String showHomePage(HttpSession session) {
        if (session.getAttribute("authenticated") != null && (boolean) session.getAttribute("authenticated")) {
            return "home";
        } else {
            return "redirect:/login";
        }
    }
}
