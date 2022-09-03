package org.manuel.springcloud.msvc.cursos.clients;

import org.manuel.springcloud.msvc.cursos.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@FeignClient(name = "msvc-usuarios", url="localhost:8001/api/v1/users")
//The second one is because in that way the url points to the msvc-usuarios container
@FeignClient(name="msvc-usuarios", url="msvc-usuarios:8001/api/v1/users")
public interface UserClientRest {

    @GetMapping("/{id}")
    User userById(@PathVariable Long id);

    @PostMapping("/save")
    User createUser(@Valid @RequestBody User user);

    @GetMapping("/users-by-course")
    List<User> getUsersByCourse(@RequestParam Iterable<Long> ids);
}
