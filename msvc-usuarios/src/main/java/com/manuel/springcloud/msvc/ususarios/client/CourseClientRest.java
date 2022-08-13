package com.manuel.springcloud.msvc.ususarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="msvc-cursos", url="host.docker.internal:8002/api/v1/courses")
public interface CourseClientRest{

    @DeleteMapping("/delete-user/{id}")
    void deleteCourseUserById(@PathVariable Long id);
}
