package com.ment09.walletservice.service;

import com.ment09.walletservice.entity.User;
import com.ment09.walletservice.repository.RoleRepository;
import com.ment09.walletservice.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User findByUserId(Long userId) {
        Optional<com.ment09.walletservice.entity.User> userOptional = userRepository.findById(userId);
        return userOptional.orElse(new User());
    }

//    public boolean saveUser(User user) {
//        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
//        if (userOptional.isPresent()) {
//            return false;
//        }
//
//    }
}
