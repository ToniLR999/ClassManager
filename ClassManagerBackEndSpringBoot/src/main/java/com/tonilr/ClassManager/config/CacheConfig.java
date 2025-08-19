package com.tonilr.ClassManager.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@Profile({"railway", "prod"})
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        
        // Configuración de caché MÍNIMA para Railway
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(100) // Máximo 100 entradas en caché
                .expireAfterWrite(300, TimeUnit.SECONDS) // Expirar después de 5 minutos
                .expireAfterAccess(300, TimeUnit.SECONDS) // Expirar después de 5 minutos sin acceso
                .recordStats() // Habilitar estadísticas para monitoreo
                .weakKeys() // Usar referencias débiles para claves
                .weakValues() // Usar referencias débiles para valores
        );
        
        return cacheManager;
    }
}
