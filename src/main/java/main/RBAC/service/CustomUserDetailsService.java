package main.RBAC.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailsService {

    public UserDetails loadUserByUsername(String rollNumber) throws UsernameNotFoundException;

}
