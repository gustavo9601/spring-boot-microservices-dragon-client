package springbootmicroservicesclientdragon.springbootmicroservicesclientdragon.controllers;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dragon-ball/characters")
public class DragonBallController {

    @Autowired
    private Faker faker;

    private List<String> characters = new ArrayList<>();

    // log
    private static final Logger logger = LoggerFactory.getLogger(DragonBallController.class);

    @GetMapping
    public ResponseEntity<List<String>> getCharacters() {
        logger.info("Ejecutando getCharacters a las :\t"+ LocalDateTime.now());
        return ResponseEntity.ok(this.characters);
    }

    @PostConstruct
    public void initCharacters() {
        for (int i = 0; i < 50; i++) {
            this.characters.add(this.faker.superhero().name());
        }
    }
}
