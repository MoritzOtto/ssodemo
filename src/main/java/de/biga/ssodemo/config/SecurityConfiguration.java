package de.biga.ssodemo.config;

import de.biga.ssodemo.security.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

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
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                //.and().addFilterBefore(new JwtTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .and()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Create dummy user service to remove security warning, not needed yet.
        return new UserService();
    }
}
