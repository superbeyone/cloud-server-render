package com.tdt.cloud.render;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author superbeyone
 */

@EnableSwagger2
@SpringBootApplication
public class CloudServerRenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudServerRenderApplication.class, args);
    }

}
