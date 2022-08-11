package org.manuel.springcloud.msvc.cursos.repositories;

import org.manuel.springcloud.msvc.cursos.models.entity.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;

public interface CourseRepository extends CrudRepository<Course, Long> {

    @Modifying
    @Query(value="delete from courses_users cu where cu.user_id=?1", nativeQuery = true)
    void deleteCourseUserById(@PathVariable Long id);
}
