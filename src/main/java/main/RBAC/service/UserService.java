package main.RBAC.service;

import main.RBAC.model.User;
import main.RBAC.response.ResponseDTO;
import main.RBAC.response.UserDetailsDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    String registerUser(User user, String roleName);

    ResponseEntity<ResponseDTO> loginUser(UserDetailsDTO userDetailsDTO);

    List<User> getAllUsers();
}
