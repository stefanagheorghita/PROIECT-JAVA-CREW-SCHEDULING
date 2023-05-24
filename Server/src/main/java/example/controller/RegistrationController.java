package example.controller;

import example.model.entity.Employee;
import example.model.entity.User;
import example.repository.UserRepository;
import example.service.EmployeeService;
import example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UserRepository userRepository;

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
    public ResponseEntity<Object> register(@RequestBody RegisterForm registerForm) {
        int employeeId = registerForm.getEmployeeId();
        String password = registerForm.getPassword();
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Password: " + password);
        Employee employee = employeeService.findEmployeeById(employeeId);

        if(employee == null) {
            return ResponseEntity.ok("Incorrect employee ID");
        }
        User user = userService.findUserByEmployeeId(employeeId);
        if(user != null) {
            return ResponseEntity.ok("User already exists");
        }
        userService.registerUser(employeeId, password);
        return ResponseEntity.ok("Account created");
    }

    public static class RegisterForm {
        private int employeeId;
        private String password;
        public RegisterForm() {
        }

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


//    @GetMapping("/login")
//    public String showSuccessPage() {
//        return "login";
//    }
}
