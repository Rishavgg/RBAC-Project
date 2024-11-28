package main.RBAC.controller;

import main.RBAC.model.User;
import main.RBAC.response.ResponseDTO;
import main.RBAC.response.UserDTO;
import main.RBAC.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/dashboard")
    public String adminDashboard() {
        return "Welcome to the Admin Dashboard!";
    }

    @GetMapping(value = "/all")
    public List<UserDTO> getAllUser() {
        return userService.getAllUsers()
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/assign-role")
    public ResponseEntity<ResponseDTO> assignRole(
            @RequestParam("username") String username,
            @RequestParam("role") String role) {
        String message = userService.assignRole(username, role);
        return ResponseEntity.ok(new ResponseDTO(message));
    }

    @PutMapping(value = "/edit-profile/{id}")
    public ResponseEntity<ResponseDTO> editUserProfile(
            @PathVariable Long id,
            @RequestBody User userDetails) {
        String message = userService.updateUserProfile(id, userDetails);
        return ResponseEntity.ok(new ResponseDTO(message));
    }
}
