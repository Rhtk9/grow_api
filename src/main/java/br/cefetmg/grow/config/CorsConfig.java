package br.cefetmg.grow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Libera todos os endpoints da API
                .allowedOrigins(
                    "http://localhost:8100", // Porta padrão do Ionic (navegador)
                    "http://localhost",       // Para testes em emuladores/dispositivos físicos
                    "http://10.0.2.2"         // IP especial que o emulador do Android usa para acessar o localhost
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH")
                .allowedHeaders("*") // Permite todos os cabeçalhos (headers)
                .allowCredentials(true); // Permite envio de cookies/autenticação se necessário futuramente
    }
}