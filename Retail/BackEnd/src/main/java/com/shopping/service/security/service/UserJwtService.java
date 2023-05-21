package com.shopping.service.security.service;

import com.shopping.service.entity.User;
import com.shopping.service.repository.sql.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserJwtService {

    @Autowired
    private UserRepository repository;

    public Optional<User> getByUserName(@NotNull String userName){
        return repository.findByUsername(userName);
    }

}
