package springbootmicroservicesclientdragon.springbootmicroservicesclientdragon.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope // habilita el endpoint [POST] /actuator/refresh // para refrescarla cache de las configuraciones
@ConfigurationProperties
public class DragonBallConfig {
    // El application.name lo importara de la configuracion del servidor de configuracion
    @Value("${application.name}")
    private String applicationName;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
