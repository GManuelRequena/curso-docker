package com.manuel.springcloud.msvc.ususarios.repositories;

import com.manuel.springcloud.msvc.ususarios.models.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
