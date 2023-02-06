package me.batchpractice.sjm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
//@EnableBatchProcessing 은 spring boot 3.0.0부터 쓰지 않음.
public class SjmApplication {

    public static void main(String[] args) {
        SpringApplication.run(SjmApplication.class, args);
    }

}
