package com.manuel.springcloud.msvc.ususarios.services;

import com.manuel.springcloud.msvc.ususarios.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();
    Optional<User> byId(Long id);
    User save(User user);
    void delete(Long id);

}
