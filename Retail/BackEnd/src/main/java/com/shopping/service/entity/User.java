package com.shopping.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_tbl")
@Builder
//@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity<Long>{
    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String surname;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Employee employee;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles= new ArrayList<>();

    public User(Long id){
        this.setId(id);
    }

    public void addRole(Role role){
        role.getUsers().add(this);
        roles.add(role);
    }
}
