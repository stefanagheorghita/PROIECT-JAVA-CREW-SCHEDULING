package example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    @GetMapping
    public String hello() {
        System.out.println("Hello World!");
        return "Hello World!";
    }
    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public String registerUser(String username, String password) {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        // Redirect to a success page or perform any other desired action
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String showSuccessPage() {
        return "success";
    }
}
