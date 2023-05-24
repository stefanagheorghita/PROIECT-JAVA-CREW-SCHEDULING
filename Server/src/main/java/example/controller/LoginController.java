package example.controller;

import example.model.entity.User;
import example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginForm loginForm) {
        int employeeId = loginForm.getEmployeeId();
        String password = loginForm.getPassword();
         boolean match=example.config.AppSecurityConfig.getPasswordEncoder().matches(password, userService.findUserByEmployeeId(employeeId).getPassword());

        User user = userService.findUserByEmployeeId(employeeId);
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Password: " + password);
       // System.out.println("Hashed Password: " + hashedPassword);
        System.out.println("Hashed Password user: " + user);
        if(user == null) {
            return ResponseEntity.ok("Incorrect employee ID");
        }
        if(!match) {
            return ResponseEntity.ok("Incorrect password");
        }
        return ResponseEntity.ok("success");
    }

    public static class LoginForm {
        private int employeeId;
        private String password;

        public int getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(int employeeId) {
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
