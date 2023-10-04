package com.example.blogapp.config;

import lombok.RequiredArgsConstructor;
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
//                .requestMatchers("/blogs/{id}/approve").hasAuthority("ROLE_MODERATOR")
//                .requestMatchers("/blogs/unapproved").hasAuthority("ROLE_MODERATOR")
                        .requestMatchers("/blogs/tiny").hasAuthority("ROLE_USER")
                        .requestMatchers("/users/logout").hasAnyAuthority("ROLE_USER","ROLE_ADMIN","ROLE_MODERATOR")
                        .requestMatchers("/blogs/approved").permitAll()
                        .requestMatchers("/blogs/unapproved").hasAnyAuthority("ROLE_MODERATOR","ROLE_ADMIN")
                        .requestMatchers("/blogs/unapproved/{id}").hasAnyAuthority("ROLE_MODERATOR","ROLE_ADMIN")
                        .requestMatchers("/blogs/reported/{id}").hasAnyAuthority("ROLE_MODERATOR","ROLE_ADMIN")
                        .requestMatchers("/blogs/reported").hasAnyAuthority("ROLE_MODERATOR","ROLE_ADMIN")
                        .requestMatchers("/comments/reported").hasAnyAuthority("ROLE_MODERATOR","ROLE_ADMIN")
                        .requestMatchers("/users/add-user").permitAll()
                        .requestMatchers("/users/verification").permitAll()
                        .requestMatchers("/users/edit").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/blogs/add-blog").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/blogs/{id}").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/blogs/edit").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/blogs/{id}/like").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/blogs/{id}/unlike").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/blogs/{id}/report").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/blogs/{id}/delete").hasAnyAuthority("ROLE_USER", "ROLE_MODERATOR")
                        .requestMatchers("/blogs/{id}/user/{userId}/comment").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/blogs/{id}/comment/{commentId}/edit").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/blogs/comment/{id}/replies").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/comments/{id}/like").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/comments/{id}/unlike").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/comments/{id}/edit").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/comments/{id}/report").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/comments/{id}/delete").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/suggestions/add").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/suggestions/blog/{blogId}").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/suggestions/{id}/reply").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/suggestions/{id}/reject").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/suggestions/{id}/edit").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/suggestions/{id}/replies").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/users/{id}").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/blogs/user/{userId}").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/suggestions/user/{userId}").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/blogs/{id}/approve").hasAnyAuthority("ROLE_MODERATOR", "ROLE_ADMIN")
                        .requestMatchers("/swagger-ui-custom.html").permitAll()
                        .requestMatchers("/swagger-ui/index.html").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()

        );
//        http.httpBasic(Customizer.withDefaults())
        http.addFilterBefore(jwtAuthFilter, BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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

