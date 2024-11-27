package main.RBAC.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String name;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        if (!this.roles.contains(role)) { // Prevent duplicates
            this.roles.add(role);
            if (!role.getUsers().contains(this)) {
                role.getUsers().add(this); // Ensure bidirectional consistency
            }
        }
    }


    public void removeRole(Role role) {
        if (this.roles.contains(role)) {
            this.roles.remove(role);
            if (role.getUsers().contains(this)) {
                role.getUsers().remove(this);
            }
        }
    }



}
