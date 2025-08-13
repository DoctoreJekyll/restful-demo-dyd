package org.generations.restfuldemodyd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
                // API REST -> sin CSRF, sin sesiones (stateless)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        // Público: Swagger y login/registro (aún no implementados, pero los dejamos abiertos)
                        .requestMatchers(
                                "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
                                "/h2-console/**", "/api/auth/**"
                        ).permitAll()

                        // Reglas de negocio:
                        // USER y ADMIN pueden crear players
                        .requestMatchers(HttpMethod.POST, "/api/players/**").hasAnyRole("USER", "ADMIN")
                        // Solo ADMIN puede borrar players
                        .requestMatchers(HttpMethod.DELETE, "/api/players/**").hasRole("ADMIN")
                        // Solo ADMIN puede crear/gestionar clases
                        .requestMatchers("/api/jobs/**").hasRole("ADMIN")
                        // Solo ADMIN puede listar/ver players en esta fase (USER solo crea)
                        .requestMatchers(HttpMethod.GET, "/api/players/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/players/**").hasRole("ADMIN")
                        // Cualquier otra ruta de la API requiere estar autenticado
                        .requestMatchers("/api/**").authenticated()
                        // Resto, abierto
                        .anyRequest().permitAll()
                )

                // Basic Auth temporal para probar con Postman
                .httpBasic(Customizer.withDefaults());

        // Para ver H2 console en navegador (opcional)
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.withUsername("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.withUsername("user")
                .password(encoder.encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
