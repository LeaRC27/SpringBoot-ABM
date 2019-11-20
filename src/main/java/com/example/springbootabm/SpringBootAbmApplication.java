package com.example.springbootabm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages={"com.example.configuracion"}/*,exclude={DataSourceAutoConfiguration.class}*/)
public class SpringBootAbmApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(SpringBootAbmApplication.class)
                .properties("spring.config.location:src/main/resources/").headless(false).run(args);

        // JFrame frame = ctx.getBean(FirstProject.class);
        // frame.setVisible(true);

    }

}
