package main.RBAC.controller;

import main.RBAC.model.User;
import main.RBAC.repository.UserRepository;
import main.RBAC.response.UserDTO;
import main.RBAC.response.UserDetailsDTO;
import main.RBAC.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
