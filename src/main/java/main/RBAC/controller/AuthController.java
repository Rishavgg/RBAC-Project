package main.RBAC.controller;

import jakarta.servlet.http.HttpServletRequest;
import main.RBAC.model.User;
import main.RBAC.response.ResponseDTO;
import main.RBAC.response.UserDetailsDTO;
import main.RBAC.security.JwtBlacklist;
import main.RBAC.security.JwtUtil;
import main.RBAC.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtBlacklist jwtBlacklist;

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody User user, @RequestParam("role") String role) {
        String message = userService.registerUser(user, role);
        return ResponseEntity.ok(new ResponseDTO(message));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody UserDetailsDTO user) {
        return userService.loginUser(user);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<ResponseDTO> logout(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);

            jwtBlacklist.blacklistToken(jwt);

            return ResponseEntity.ok(new ResponseDTO("Logout successful"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO("No token found in request"));
    }
}