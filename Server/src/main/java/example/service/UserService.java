package example.service;

import example.model.entity.User;
import example.repository.EmployeeRepository;
import example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {



    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public void registerUser(int employeeId, String password) {
        User user = new User();
        user.setEmployee(employeeRepository.findById(employeeId).get());
        String hashedPassword = example.config.AppSecurityConfig.getPasswordEncoder().encode(password);
        //user.setId(userRepository.findAll().size() + 1);
        user.setPassword(hashedPassword);
        userRepository.save(user);

       // String hashedPassword = AppSecurityConfig.getPasswordEncoder().encode(user.getPassword());
       // user.setPassword(hashedPassword);
        //userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername");
        System.out.println(username);
        try {
            int id = Integer.parseInt(username);
            User user = userRepository.findByEmployeeId(id);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return user;
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid username format");
        }
    }

    public User findUserByEmployeeId(int id) {
        return userRepository.findByEmployeeId(id);
    }
}
