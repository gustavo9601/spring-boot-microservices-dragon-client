package springbootmicroservicesclientdragon.springbootmicroservicesclientdragon.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MockTranslationSlowServiceTest {

    Map<String, String> traducciones = new HashMap<>();

    @PostConstruct
    private void init() {
        traducciones.put("hola", "hello");
        traducciones.put("adios", "bye");
        traducciones.put("perro", "dog");
    }

    public Optional<String> getTranslation(String word) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(traducciones.get(word));
    }

    @CacheEvict(value = {"translationsMock"}, allEntries = true) // con esta anotacion se limpia el cache, pasada por parametro
    public void clearCache() {

    }

}
