package example.service;

import example.model.entity.User;
import example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registerUser(User user) {
       // String hashedPassword = AppSecurityConfig.getPasswordEncoder().encode(user.getPassword());
       // user.setPassword(hashedPassword);
        //userRepository.save(user);
    }
}
