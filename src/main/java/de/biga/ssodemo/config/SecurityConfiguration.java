package de.biga.ssodemo.config;

import de.biga.ssodemo.app.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    /*private final TokenProvider tokenProvider;

    public SecurityConfiguration(TokenProvider tokenProvider) {

        this.tokenProvider = tokenProvider;
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                //.authorizeHttpRequests()
                //.anyRequest().authenticated()
                //.and().addFilterBefore(new JwtTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                //.and()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Create dummy user service to remove security warning, not needed yet.
        return new UserService();
    }
}
