package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        SecurityFilter securityFilter = new SecurityFilter(); // Instancia de tu filtro personalizado

        return http
                .csrf(csrf -> csrf.disable()) // Deshabilita la protección CSRF
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Configura sesiones sin estado
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/login").permitAll() // Permite acceso sin autenticación a /login para métodos POST
                        .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Agrega el filtro personalizado antes del filtro de autenticación
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
