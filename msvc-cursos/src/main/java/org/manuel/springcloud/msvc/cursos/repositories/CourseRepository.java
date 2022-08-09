package org.manuel.springcloud.msvc.cursos.repositories;

import org.manuel.springcloud.msvc.cursos.models.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {

}
