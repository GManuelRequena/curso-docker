package org.manuel.springcloud.msvc.cursos.services;

import org.manuel.springcloud.msvc.cursos.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> getAll();
    Optional<Course> byId(Long id);
    Course save(Course course);
    void delete(Long id);
}
