package com.example.blogapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    final private JWTService jwtService;

    final private CorsConfigurationSource corsConfigurationSource;
    final private UserDetailsService userDetailsService;
    final private JWTAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        //http.cors(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());
//        http.cors(
//                httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource)
//        );
//        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
//        http.httpBasic(Customizer.withDefaults());
//        http.csrf(AbstractHttpConfigurer::disable);
//        http.cors(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/users/login").permitAll()
                .requestMatchers("/blogs/tiny").hasAuthority("ROLE_USER")
                .requestMatchers("/blogs/all").hasAuthority("ROLE_USER")
                .requestMatchers("/users/add-user").permitAll()
                .requestMatchers("/users/verification").permitAll()
                .requestMatchers("/users/{id}").permitAll()
                .requestMatchers("/users/edit").permitAll()
                .requestMatchers("/swagger-ui-custom.html").permitAll()
                .requestMatchers("/swagger-ui/index.html").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/blogs/add-blog").hasAuthority("ROLE_USER")
                .requestMatchers("/blogs/{id}").hasAuthority("ROLE_USER")
                .requestMatchers("/blogs/edit").hasAuthority("ROLE_USER")
                .requestMatchers("/blogs/{id}/like").hasAuthority("ROLE_USER")
                .requestMatchers("/blogs/{id}/unlike").hasAuthority("ROLE_USER")
                .requestMatchers("/blogs/{id}/report").hasAuthority("ROLE_USER")
                .requestMatchers("/blogs/{id}/delete").hasAuthority("ROLE_USER")
                .requestMatchers("/blogs/{id}/user/{userId}/comment").hasAuthority("ROLE_USER")
                .requestMatchers("/blogs/{id}/comment/{commentId}/edit").hasAuthority("ROLE_USER")
                .requestMatchers("/blogs/comment/{id}/replies").hasAuthority("ROLE_USER")
                .requestMatchers("/comments/{id}/like").hasAuthority("ROLE_USER")
                .requestMatchers("/comments/{id}/unlike").hasAuthority("ROLE_USER")
                .requestMatchers("/comments/{id}/edit").hasAuthority("ROLE_USER")
                .requestMatchers("/comments/{id}/report").hasAuthority("ROLE_USER")
                .requestMatchers("/comments/{id}/delete").hasAuthority("ROLE_USER")
                .requestMatchers("/suggestions/add").hasAuthority("ROLE_USER")
        );
//        http.httpBasic(Customizer.withDefaults())
        http.addFilterBefore(jwtAuthFilter, BasicAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("*"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH","DELETE","OPTIONS"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
@Bean
public CorsFilter corsFilter() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.setAllowedOrigins(Constants.CORS_ALLOWED_ORIGINS);
    corsConfiguration.setAllowedHeaders(Constants.CORS_ALLOWED_HEADERS);
    corsConfiguration.setExposedHeaders(Constants.CORS_EXPOSED_HEADERS);
    corsConfiguration.setAllowedMethods(Constants.CORS_ALLOWED_METHODS);
    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    urlBasedCorsConfigurationSource.registerCorsConfiguration(Constants.CORS_CONFIGURATION_PATTERN, corsConfiguration);
    return new CorsFilter(urlBasedCorsConfigurationSource);
}


}

