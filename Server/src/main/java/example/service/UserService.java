package example.service;

import example.model.entity.User;
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

    public void registerUser(User user) {
        String hashedPassword = example.config.AppSecurityConfig.getPasswordEncoder().encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Long id = Long.parseLong(username);
        User user = userRepository.findByEmployeeId(id);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
