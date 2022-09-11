package springbootmicroservicesclientdragon.springbootmicroservicesclientdragon.configs;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FakerConfig {

    @Bean
    public Faker buildFaker(){
        return new Faker();
    }

}
