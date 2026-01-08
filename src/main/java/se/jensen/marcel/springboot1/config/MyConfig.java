package se.jensen.marcel.springboot1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class MyConfig {

    @Bean
    public Scanner scanner(){
        return new Scanner(System.in);
    }


}
