package com.example.board.service;

import com.example.board.dto.UserDTO;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // BCrypt 인코더 추가

    // 新規登録
    public void save(UserDTO userDTO) {
        // 使用者照会
        UserDTO existingUser = userRepository.findByUsername(userDTO.getUsername());
        // 使用者確認
        if (existingUser != null) {
            throw new RuntimeException("Username already exists");
        }

        // パソワード暗号化
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);

        // 使用者セーフ
        userRepository.save(userDTO);
    }

    // 使用者ネーム照会
    public UserDTO findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 全体使用者照会
    public List<UserDTO> findAll() {
        return userRepository.findAll();
    }

    // ログインの検証
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
