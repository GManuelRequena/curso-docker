package com.manuel.springcloud.msvc.ususarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="msvc-cursos", url="host.docker.internal:8002/api/v1/courses")
//The second one is because in that way the url points to the msvc-cursos container
@FeignClient(name="msvc-cursos", url="${msvc.cursos.url}")
public interface CourseClientRest{

    @DeleteMapping("/delete-user/{id}")
    void deleteCourseUserById(@PathVariable Long id);
}
