package pe.edu.utp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permitir CORS para todas las rutas
                .allowedOrigins("http://127.0.0.1:5500", "https://home-rescue-a322e.web.app/","http://127.0.0.1:5501","https://enough-polite-gibbon.ngrok-free.app/") // URLs de tu frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                .allowedHeaders("*") // Permitir todos los headers
                .allowCredentials(true); // Permitir cookies y credenciales
    }
}
