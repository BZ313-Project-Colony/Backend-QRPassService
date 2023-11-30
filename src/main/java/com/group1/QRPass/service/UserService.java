package com.group1.QRPass.service;
import com.group1.QRPass.model.Role;
import com.group1.QRPass.model.User;
import com.group1.QRPass.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User is not valid"));
    }
    protected User createUser(String username, String password, Set<Role> authorities){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAuthorities(authorities);
        return userRepository.save(user);
    }

    protected boolean isUserExistByName(String username){
        return userRepository.findByUsername(username).isPresent();
    }
}
