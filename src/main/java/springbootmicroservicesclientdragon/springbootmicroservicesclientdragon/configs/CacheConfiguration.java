package springbootmicroservicesclientdragon.springbootmicroservicesclientdragon.configs;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@EnableCaching
@Configuration
public class CacheConfiguration {




    @Bean
    public ConcurrentMapCacheManager getManager() {
        return new ConcurrentMapCacheManager("translationsMock");
    }

    // Configuracion de cache en redis
    /*@Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        // Especificando el host y el puerto

        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }*/

    /*@Bean
    public CacheManager getManager(RedissonClient redissonClient) {
        // Configuracion de cache en memoria
        *//*return new ConcurrentMapCacheManager("translationsMock");*//*

        Map<String, CacheConfig> configMap = new HashMap<>();
        // Se añade los diferentes keys que se van a cachear
        configMap.put("translationsMock", new CacheConfig());
        return new RedissonSpringCacheManager(redissonClient);

    }*/


}
