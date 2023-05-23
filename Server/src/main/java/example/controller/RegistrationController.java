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
    public String register(@RequestBody RegisterForm registerForm) {
        String employeeId = registerForm.getEmployeeId();
        String password = registerForm.getPassword();

        // Process the registration data
        // ...

        System.out.println("Employee ID: " + employeeId);
        System.out.println("Password: " + password);

        // Redirect to a success page or perform any other desired action
        return "Registration successful";
    }

    // Inner class for the registration form
    public static class RegisterForm {
        private String employeeId;
        private String password;

        // Default constructor
        public RegisterForm() {
        }

        // Getters and setters
        public String getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(String employeeId) {
            this.employeeId = employeeId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }


    @GetMapping("/success")
    public String showSuccessPage() {
        return "success";
    }
}
