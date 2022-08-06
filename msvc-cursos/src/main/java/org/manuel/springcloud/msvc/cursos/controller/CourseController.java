package org.manuel.springcloud.msvc.cursos.controller;

import org.manuel.springcloud.msvc.cursos.entity.Course;
import org.manuel.springcloud.msvc.cursos.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<?> save(@RequestBody Course course) {
        Course courseDB = courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editById(@RequestBody Course course, @PathVariable Long id) {
        Optional<Course> courseId = courseService.byId(id);
        if (courseId.isPresent()) {
            Course courseDB = courseId.get();
            courseDB.setNombre(course.getNombre());
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
}
