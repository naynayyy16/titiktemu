package com.stis.titiktemu.service;

import com.stis.titiktemu.dto.ChangePasswordRequest;
import com.stis.titiktemu.dto.UpdateProfileRequest;
import com.stis.titiktemu.dto.UserResponse;
import com.stis.titiktemu.model.User;
import com.stis.titiktemu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get current logged in user
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
    }

    // Get profile
    public UserResponse getProfile() {
        User user = getCurrentUser();
        return mapToUserResponse(user);
    }

    // Update profile
    public UserResponse updateProfile(UpdateProfileRequest request) {
        User user = getCurrentUser();

        // Check if email is being changed and already exists
        if (!user.getEmail().equals(request.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email sudah digunakan oleh user lain");
            }
        }

        user.setNamaLengkap(request.getNamaLengkap());
        user.setJabatan(request.getJabatan());
        user.setNimNip(request.getNimNip());
        user.setNoHp(request.getNoHp());
        user.setEmail(request.getEmail());

        userRepository.save(user);

        return mapToUserResponse(user);
    }

    // Change password
    public void changePassword(ChangePasswordRequest request) {
        User user = getCurrentUser();

        // Verify old password
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Password lama tidak sesuai");
        }

        // Update to new password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    // Delete account
    public void deleteAccount() {
        User user = getCurrentUser();
        userRepository.delete(user);
    }

    // Helper method to map User to UserResponse
    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setNamaLengkap(user.getNamaLengkap());
        response.setJabatan(user.getJabatan());
        response.setNimNip(user.getNimNip());
        response.setNoHp(user.getNoHp());
        return response;
    }
}