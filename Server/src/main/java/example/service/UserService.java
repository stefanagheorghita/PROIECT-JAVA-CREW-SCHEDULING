package example.service;

import example.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registerUser(User user) {
        String hashedPassword = AppSecurityConfig.getPasswordEncoder().encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
}
