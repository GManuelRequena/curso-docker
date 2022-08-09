package org.manuel.springcloud.msvc.cursos.services;

import org.manuel.springcloud.msvc.cursos.models.User;
import org.manuel.springcloud.msvc.cursos.models.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> getAll();
    Optional<Course> byId(Long id);
    Course save(Course course);
    void delete(Long id);
    Optional<User> assignUser(User user, Long courseId);
    Optional<User> createUser(User user, Long courseId);
    Optional<User> unassignUser(User user, Long courseId);
}
