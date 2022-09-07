package springbootmicroservicesclientdragon.springbootmicroservicesclientdragon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springbootmicroservicesclientdragon.springbootmicroservicesclientdragon.configs.DragonBallConfig;

@RestController
@RequestMapping("/application-name")
public class ApplicationNameController {

    @Autowired
    private DragonBallConfig dragonBallConfig;

    @GetMapping
    public ResponseEntity<String> getAppName() {
        return ResponseEntity.ok(this.dragonBallConfig.getApplicationName());
    }
}
