package com.manuel.springcloud.msvc.ususarios.controllers;

import com.manuel.springcloud.msvc.ususarios.models.entity.User;
import com.manuel.springcloud.msvc.ususarios.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("api/v1/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/get-all")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> userById(@PathVariable Long id) {
        Optional<User> user = userService.byId(id);
        if (user.isPresent()) {
            //return  ResponseEntity.ok().body(user.get());
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@RequestBody User user, @PathVariable Long id) {
        Optional<User> userId = userService.byId(id);
        if (userId.isPresent()) {
            User userDB = userId.get();
            userDB.setName(user.getName());
            userDB.setEmail(user.getEmail());
            userDB.setPassword(user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        Optional<User> userId = userService.byId(id);
        if(userId.isPresent()){
            userService.delete(userId.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
