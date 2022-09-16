package springbootmicroservicesclientdragon.springbootmicroservicesclientdragon.controllers;

import com.github.javafaker.Faker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springbootmicroservicesclientdragon.springbootmicroservicesclientdragon.services.FooServiceZipkin;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/*
 * Spring Doc + Swagger UI
 * http://localhost:8082/v3/api-docs
 * http://localhost:8082/swagger-ui/index.html
 * */

@RestController
@RequestMapping("/api/v1/dragon-ball/characters")
public class DragonBallController {

    @Autowired
    private Faker faker;

    private List<String> characters = new ArrayList<>();

    @Autowired
    private FooServiceZipkin fooServiceZipkin;

    // log
    private static final Logger logger = LoggerFactory.getLogger(DragonBallController.class);

    @GetMapping
    @Operation(summary = "Get a list of all characters") // Resumen del endpoint
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of all characters",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = String.class))) // Tipo de dato que devuelve
            ),

            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404", description = "Not Found characteres")
    })
    public ResponseEntity<List<String>> getCharacters() {
        logger.info("Ejecutando getCharacters a las :\t" + LocalDateTime.now());
        this.fooServiceZipkin.printLog();
        return ResponseEntity.ok(this.characters);
    }


    @GetMapping("/{name}")
    @Operation(summary = "Find and get the character by name") // Resumen del endpoint
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get the character",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) // Tipo de dato que devuelve
            ),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404", description = "Not Found charactere")
    })
    public ResponseEntity<String> getCharacterByName(@PathVariable(value = "name") String name) throws HttpException {
        logger.info("Ejecutando getCharacterByName a las :\t" + LocalDateTime.now());

        Optional<String> characterFounded = this.characters.stream()
                .filter(character -> character.equals(name))
                .findFirst();
        if (characterFounded.isEmpty()) {
            throw new HttpException("Character not found");
        }
        return ResponseEntity.ok(characterFounded.get());
    }

    @PostConstruct
    public void initCharacters() {
        for (int i = 0; i < 50; i++) {
            this.characters.add(this.faker.superhero().name());
        }
    }
}
