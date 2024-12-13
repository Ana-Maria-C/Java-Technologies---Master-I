package com.example.laborator9.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_roles")
public class UserRoles {

    @Id
    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private User user;

    @Id
    private String role_name;
}
