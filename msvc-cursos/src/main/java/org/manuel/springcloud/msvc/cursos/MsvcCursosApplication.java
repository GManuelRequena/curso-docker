package org.manuel.springcloud.msvc.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableFeignClients
public class MsvcCursosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcCursosApplication.class, args);
    }
}
