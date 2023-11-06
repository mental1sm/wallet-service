package com.ment09.walletservice.service;

import com.ment09.walletservice.entity.Role;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EntityManager entityManager;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findByUserId(Long userId) {
        Optional<com.ment09.walletservice.entity.User> userOptional = userRepository.findById(userId);
        return userOptional.orElse(null);
    }

    public boolean saveUser(User user) {
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        if (userOptional.isPresent()) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    public boolean inRoleByUsername(String username, String checkingRole) {
        User user = userRepository.findByUsername(username).orElse(new User());
        for (Role role : user.getRoles()) {
            if (checkingRole.contentEquals(role.getName())) {
                return true;
            }
        }
        return false;
    }
}
