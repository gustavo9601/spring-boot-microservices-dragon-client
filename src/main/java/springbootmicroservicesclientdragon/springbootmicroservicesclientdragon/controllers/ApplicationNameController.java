package springbootmicroservicesclientdragon.springbootmicroservicesclientdragon.controllers;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import springbootmicroservicesclientdragon.springbootmicroservicesclientdragon.configs.DragonBallConfig;
import springbootmicroservicesclientdragon.springbootmicroservicesclientdragon.services.MockTranslationSlowServiceTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

@RestController
@RequestMapping("/application-name")
public class ApplicationNameController {


    @Autowired
    private MockTranslationSlowServiceTest mockTranslationSlowServiceTest;

    // Se inyecta para poder crear nuestra propia mestrica
    @Autowired
    private MeterRegistry meterRegistry;
    /*
     * Para consultar las metricas del ejemplo:
     * http://localhost:8082/actuator/metrics/application-name-counter
     *
     * Para filtrar por el tag
     * http://localhost:8082/actuator/metrics/application-name-counter?tag=<<name_tag>>:<<key_tag>>
     * http://localhost:8082/actuator/metrics/application-name-counter?tag=level:low
     * */


    @Autowired
    private DragonBallConfig dragonBallConfig;

    private static final Logger logger = Logger.getLogger(ApplicationNameController.class.getName());

    @GetMapping
    public ResponseEntity<String> getAppName() {
        logger.info("Ejecutando getAppName a las :\t" + LocalDateTime.now());

        // .increment() es para incrementar el contador, si se le pasa un parametro le sumara N al contador
        int random = new Random().nextInt(100);
        this.meterRegistry.counter("application-name-counter", // nombre de la metrica
                "level",  // tag opcional para despues filtrar
                (random > 50) ? "high" : "low"  // valor del tag
        ).increment(random);

        return ResponseEntity.ok(this.dragonBallConfig.getApplicationName());
    }

    /*
     * Timed:
     * http://localhost:8082/actuator/metrics/application-name-timed-get
     *
     * Añadira metricas de cantidad de peticiones, ultimo request en tiempo, y el maximo en demora, entre otros
     * */
    @Timed(value = "application-name-timed-get")
    @GetMapping("/timed")
    public ResponseEntity<String> getAppNameTimed() {
        logger.info("Ejecutando getAppNameTimed a las :\t" + LocalDateTime.now());


        Integer valueInteger = new Random().nextInt(100);
        if (valueInteger < 50) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (valueInteger > 80) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error");
        }

        return ResponseEntity.ok(this.dragonBallConfig.getApplicationName());
    }


    @GetMapping("/translation-slow/{word}")
    @Cacheable("translationsMock") // Especifica que se cachera, debe estar registrao en el CacheManager
    public ResponseEntity<String> getNameTranslationSlow(@PathVariable String word) {
        logger.info("Ejecutando getNameSlow a las :\t" + LocalDateTime.now());

        Optional<String> wordTranslated = this.mockTranslationSlowServiceTest.getTranslation(word);

        if (wordTranslated.isPresent()) {
            return ResponseEntity.ok(wordTranslated.get());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/clean-cache")
    public ResponseEntity<Void> clearCache(){
        logger.info("Ejecutando clearCache a las :\t" + LocalDateTime.now());
        this.mockTranslationSlowServiceTest.clearCache();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
