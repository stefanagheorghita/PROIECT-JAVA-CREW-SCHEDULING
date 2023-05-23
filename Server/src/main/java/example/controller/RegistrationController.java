package example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;

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
        // Add your logic here to process the submitted data
        // For example, you can store the user information in a database
        // or perform any necessary validation or business logic
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
