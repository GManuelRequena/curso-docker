package com.manuel.springcloud.msvc.ususarios.controllers;

import com.manuel.springcloud.msvc.ususarios.models.entity.User;
import com.manuel.springcloud.msvc.ususarios.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController()
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    private UserService getUserService() {
        return this.userService;
    }

    @ApiOperation(value = "URL for get all users.")
    @ApiResponse(code = 200, message = "OK")
    @GetMapping("/get-all")
    public List<User> getAll() {
        return getUserService().getAll();
    }

    @ApiOperation(value = "URL for get user by ID.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "User not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> userById(@PathVariable Long id) {
        Optional<User> user = getUserService().byId(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @ApiOperation(value = "URL for create user.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User created successfully."),
            @ApiResponse(code = 400, message = "E-mail can't be empty or it already exists.")
    })
    @PostMapping("/save")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return validate(result);
        }
        if (!user.getEmail().isEmpty()) {
            if (!getUserService().existsByEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.CREATED).body(getUserService().save(user));
            } else {
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("message", "E-mail already exists.")
                        );
            }
        } else {
            return ResponseEntity.badRequest().body("E-mail can't be empty.");
        }
    }

    @ApiOperation(value = "URL for edit an user.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User edited successfully."),
            @ApiResponse(code = 400, message = "E-mail can't be empty or it already exists."),
            @ApiResponse(code = 404, message = "User not found.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validate(result);
        }
        Optional<User> userId = getUserService().byId(id);
        if (userId.isPresent()) {
            User userDB = userId.get();
            if (user.getEmail().isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail can't be empty.");
            }else{
                if(getUserService().byEmail(user.getEmail()).isPresent()){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail already exists.");
                }else {
                    userDB.setName(user.getName());
                    userDB.setEmail(user.getEmail());
                    userDB.setPassword(user.getPassword());
                    return ResponseEntity.status(HttpStatus.CREATED).body(getUserService().save(userDB));
                }

            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @ApiOperation(value = "URL for delete a user.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "User deleted successfully."),
            @ApiResponse(code = 404, message = "User not found.")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> userId = getUserService().byId(id);
        if (userId.isPresent()) {
            userService.delete(userId.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
