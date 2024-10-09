package com.example.board.controller;

import com.example.board.dto.UserDTO;
import com.example.board.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// @RequiredArgsConstructorはfinalフィールドに対して生成者を自動的に生成
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "registration";
    }

    // 新規登録
    @PostMapping("/registration")
    public String registerUser(UserDTO userDTO, Model model) {
        try {
            userService.save(userDTO);
            // 成功時にログインページにリダイレクトします。
            return "redirect:/login";
        } catch (RuntimeException e) {
            // すでに存在する場合、エラーメッセージをモデルに追加
            model.addAttribute("errorMessage", "Username already exists");
            model.addAttribute("userDTO", userDTO);
            return "registration";
        }
    }

    // ログインページ
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "login";
    }

    // ログイン処理
    @PostMapping("/login")
    public String loginUser(UserDTO userDTO, Model model, HttpSession session) {
        // 使用者照会
        UserDTO existingUser = userService.findByUsername(userDTO.getUsername());

        // パスワード確認 (BCryptによる検証)
        if (existingUser != null && userService.checkPassword(userDTO.getPassword(), existingUser.getPassword())) {
            session.setAttribute("loggedInUser", existingUser);
            return "redirect:/";
        } else {
            // ログインエラー
            model.addAttribute("loginError", true);
            model.addAttribute("userDTO", userDTO);
            return "login";
        }
    }

    // ログアウト
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // メインページの情報
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");
        // ログイン確認
        if (loggedInUser != null) {
            model.addAttribute("username", loggedInUser.getUsername());
            model.addAttribute("isLoggedIn", true);
        } else {
            model.addAttribute("isLoggedIn", false);
        }
        return "index";
    }
}
