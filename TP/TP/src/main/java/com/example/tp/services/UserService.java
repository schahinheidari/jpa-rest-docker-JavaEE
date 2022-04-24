package com.example.tp.services;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.tp.controllers.dto.DtoUserRegisterC;
import com.example.tp.entities.User;

public interface UserService extends UserDetailsService {

    User getUser(String email);

    User save(DtoUserRegisterC registration);

    User save(@Valid @NotNull User user);

}
