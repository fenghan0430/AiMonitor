package com.example.aimonitor.controller;

import com.example.aimonitor.util.MD5Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class LoginController {

    @Value("${login.username}")
    private String expectedUsername;

    @Value("${login.password}")
    private String expectedPassword;

    // 登录接口
    @PostMapping("/login-check")
    public ResponseEntity<Map<String, String>> login(@RequestParam String username, @RequestParam String password) {
        Map<String, String> response = new HashMap<>();

        String md5Password = MD5Util.encrypt(expectedPassword);

        if (username.equals(expectedUsername) && password.equals(md5Password)) {
            // 假设登录成功返回一个简单的消息或 token
            response.put("message", "Login successful");
            response.put("token", "fake-jwt-token-for-demo");  // 示例 token，实际使用 JWT 等
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
