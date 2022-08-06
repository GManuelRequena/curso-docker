package org.manuel.springcloud.msvc.cursos.controller;

import org.manuel.springcloud.msvc.cursos.entity.Course;
import org.manuel.springcloud.msvc.cursos.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/get-all")
    public ResponseEntity<List<Course>> getAll() {
        return ResponseEntity.ok(courseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable Long id) {
        Optional<Course> courseId = courseService.byId(id);
        /*if (courseId.isPresent()) {
            return ResponseEntity.ok(courseId.get());
        } else {
            return ResponseEntity.notFound().build();
        }*/
        return courseId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody Course course, BindingResult result) {
        if(result.hasErrors()){
            return validate(result);
        }
        Course courseDB = courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editById(@Valid @RequestBody Course course, BindingResult result, @PathVariable Long id) {
        if(result.hasErrors()){
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Course> courseDB = courseService.byId(id);
        if (courseDB.isPresent()) {
            courseService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
