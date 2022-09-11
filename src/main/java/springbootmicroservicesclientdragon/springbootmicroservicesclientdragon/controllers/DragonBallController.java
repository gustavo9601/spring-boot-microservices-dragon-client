package springbootmicroservicesclientdragon.springbootmicroservicesclientdragon.controllers;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dragon-ball/characters")
public class DragonBallController {

    @Autowired
    private Faker faker;

    private List<String> characters = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<String>> getCharacters() {
        return ResponseEntity.ok(this.characters);
    }

    @PostConstruct
    public void initCharacters() {
        for (int i = 0; i < 50; i++) {
            this.characters.add(this.faker.superhero().name());
        }
    }
}
