package main.RBAC.controller;

import main.RBAC.security.JwtUtil;
import main.RBAC.service.UserService;
import main.RBAC.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value = "/register")
    public String register(@RequestBody User user) {
        userService.registerUser(user);
        return "User registered successfully!";
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody User user) {
        return jwtUtil.generateToken(user.getUsername());
    }
}