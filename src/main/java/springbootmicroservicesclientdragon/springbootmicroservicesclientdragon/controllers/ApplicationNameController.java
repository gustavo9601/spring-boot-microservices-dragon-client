package springbootmicroservicesclientdragon.springbootmicroservicesclientdragon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springbootmicroservicesclientdragon.springbootmicroservicesclientdragon.configs.DragonBallConfig;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@RestController
@RequestMapping("/application-name")
public class ApplicationNameController {

    @Autowired
    private DragonBallConfig dragonBallConfig;

    private static final Logger logger = Logger.getLogger(ApplicationNameController.class.getName());

    @GetMapping
    public ResponseEntity<String> getAppName() {
        logger.info("Ejecutando getAppName a las :\t"+ LocalDateTime.now());
        return ResponseEntity.ok(this.dragonBallConfig.getApplicationName());
    }
}
