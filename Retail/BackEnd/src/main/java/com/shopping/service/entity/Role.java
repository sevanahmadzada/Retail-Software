package com.shopping.service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role extends BaseEntity<Long> implements GrantedAuthority {

    @Column(length = 100, nullable = false)
    private String rollName;

    @Column(length = 200)
    private String rollDesc;

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    public void addUser(User user){
        users.add(user);
        user.getRoles().add(this);
    }

    @Override
    public String getAuthority() {
        return rollName;
    }
}
