package org.manuel.springcloud.msvc.cursos.controller;

import feign.FeignException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.manuel.springcloud.msvc.cursos.models.User;
import org.manuel.springcloud.msvc.cursos.models.entity.Course;
import org.manuel.springcloud.msvc.cursos.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("api/v1/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "Get all courses")
    @GetMapping("/get-all")
    public ResponseEntity<List<Course>> getAll() {
        return ResponseEntity.ok(courseService.getAll());
    }

    @ApiOperation(value = "Get course by ID")
    @ApiImplicitParam(value = "Course ID to search", name = "id")
    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable Long id) {
        Optional<Course> courseId = courseService.byIdWithUsers(id);
        return courseId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Create a course")
    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody Course course, BindingResult result) {
        if (result.hasErrors()) {
            return validate(result);
        }
        Course courseDB = courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDB);
    }

    @ApiOperation(value = "Edit a course")
    @ApiImplicitParam(value = "Course ID", name = "id")
    @PutMapping("/{id}")
    public ResponseEntity<?> editById(@Valid @RequestBody Course course, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validate(result);
        }
        Optional<Course> courseId = courseService.byId(id);
        if (courseId.isPresent()) {
            Course courseDB = courseId.get();
            courseDB.setName(course.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseDB));
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Delete a course")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Course ID", name = "id")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Course> courseDB = courseService.byId(id);
        if (courseDB.isPresent()) {
            courseService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Assign user to course")
    @ApiImplicitParam(value = "Course ID", name = "id")
    @PutMapping("/assign-user/{id}")
    public ResponseEntity<?> assignUser(@Valid @RequestBody User user, @PathVariable Long id) {
        Optional<User> o;
        try {
            o = courseService.assignUser(user, id);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "No existe el id o error de comunicacion " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Create user ")
    @ApiImplicitParam(value = "Course ID", name = "id")
    @PutMapping("/create-user/{id}")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, @PathVariable Long id) {
        Optional<User> o;
        try {
            o = courseService.createUser(user, id);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "No se pudo crear el usuario o error de comunicacion " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Unassign user")
    @ApiImplicitParam(value = "Course ID", name = "id")
    @DeleteMapping("/unassign-user/{id}")
    public ResponseEntity<?> unassignUser(@Valid @RequestBody User user, @PathVariable Long id) {
        Optional<User> o;
        try {
            o = courseService.unassignUser(user, id);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "No existe el id o error de comunicacion " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteCourseUserById(@PathVariable Long id){
        courseService.deleteCourseUserById(id);
        return ResponseEntity.noContent().build();
    }
    private ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
