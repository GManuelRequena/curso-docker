package org.manuel.springcloud.msvc.cursos.clients;

import org.manuel.springcloud.msvc.cursos.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "msvc-usuarios", url="localhost:8001")
public interface UserClientRest {

    @GetMapping("/api/v1/users/{id}")
    User userById(@PathVariable Long id);

    @PostMapping("/api/v1/users/save")
    User createUser(@Valid @RequestBody User user);
}
