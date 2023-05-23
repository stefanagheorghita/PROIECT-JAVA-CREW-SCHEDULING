package example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginForm loginForm) {
        String employeeId = loginForm.getEmployeeId();
        String password = loginForm.getPassword();

        // Process the submitted data
        // ...

        System.out.println("Employee ID: " + employeeId);
        System.out.println("Password: " + password);

        // Redirect to a success page or perform any other desired action
        return "Login successful";
    }

    public static class LoginForm {
        private String employeeId;
        private String password;

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

}
