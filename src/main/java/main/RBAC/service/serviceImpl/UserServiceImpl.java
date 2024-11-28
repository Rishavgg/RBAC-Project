package main.RBAC.service.serviceImpl;

import main.RBAC.model.Role;
import main.RBAC.model.User;
import main.RBAC.repository.RoleRepository;
import main.RBAC.repository.UserRepository;
import main.RBAC.response.ResponseDTO;
import main.RBAC.response.UserDetailsDTO;
import main.RBAC.security.JwtUtil;
import main.RBAC.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String registerUser(User user, String roleName) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "Username already exists";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepository.findByName(roleName.toUpperCase());
        if (role == null) {
            role = new Role();
            role.setName(roleName.toUpperCase());
            roleRepository.save(role);
        }

        Set<Role> userRoles = new HashSet<>(user.getRoles()); // Defensive copy
        userRoles.add(role);
        user.setRoles(userRoles);

        userRepository.save(user);
        return "User Added Successfully";
    }

    @Override
    public ResponseEntity<ResponseDTO> loginUser(UserDetailsDTO userDetailsDTO) {
        try {
            User user = userRepository.findByUsername(userDetailsDTO.getUsername());
            if (user == null) {
                return ResponseEntity.ok(new ResponseDTO("Student not found"));
            }
            if (!passwordEncoder.matches(userDetailsDTO.getPassword(), user.getPassword())) {
                return ResponseEntity.ok(new ResponseDTO("Wrong Credential"));
            }

            String token = jwtUtil.generateToken(userDetailsDTO.getUsername());

            return ResponseEntity.ok(new ResponseDTO("Login successful", token));

        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseDTO("Wrong Credential"));
        }
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public String assignRole(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return "User not found";
        }

        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            return "Role not found";
        }

        user.addRole(role);
        userRepository.save(user);
        return "Role assigned successfully";
    }

    @Override
    public String updateUserProfile(Long id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();
        user.setName(userDetails.getName());
        user.setUsername(userDetails.getUsername());
        userRepository.save(user);
        return "User profile updated successfully";
    }

}
