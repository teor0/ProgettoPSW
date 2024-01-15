package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ApplicationBooter {

    public static void main(String [] args){
        SpringApplication.run(ApplicationBooter.class, args);
    }

    /*come suggerito da https://spring.io/guides/gs/rest-service-cors/#controller-method-cors-configuration
    purtroppo non funziona
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("https://localhost:4200");
            }
        };
    }*/

}
