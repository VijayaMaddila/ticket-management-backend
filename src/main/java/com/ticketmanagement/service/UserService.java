package com.ticketmanagement.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ticketmanagement.model.User;
import com.ticketmanagement.repository.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    
    public User createUser(User user) {
        String email = user.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

    public User findByEmailForLogin(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    
    public User updateUserRole(Long userId, User updatedUser) {
        User existingUser = getUser(userId);
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        return userRepository.save(existingUser);
    }

    public User updateProfile(Long userId, String name, String password) {
        User user = getUser(userId);
        user.setName(name);
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        return userRepository.save(user);
    }

    public User updateProfilePhoto(Long userId, MultipartFile file) throws IOException {
        User user = getUser(userId);
        if (file != null && !file.isEmpty()) {
            user.setPhoto(file.getBytes());
        }
        return userRepository.save(user);
    }

    public byte[] getProfilePhoto(Long userId) {
        User user = getUser(userId);
        return user.getPhoto(); 
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}
