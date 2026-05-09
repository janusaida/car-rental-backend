
package com.nikhil.carrental.backend.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())   // ✅ disable CSRF
                .cors(cors -> {})               // ✅ enable CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()  // ✅ allow login/register
                        .requestMatchers("/api/cars/**").permitAll()  // ✅ allow cars
                        .requestMatchers("/api/bookings/**").permitAll()
                        .anyRequest().permitAll()  // 🔥 TEMP: allow all
                );

        return http.build();
    }
}
    // 🌐 CORS CONFIGURATION (MAIN FIX)
//public class SecurityConfig {
//
//    private final JwtFilter jwtFilter;
//
//    // 🔐 PASSWORD ENCODER
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    // 🔐 AUTH MANAGER
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//    // 🔐 SECURITY FILTER CHAIN
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .csrf(csrf -> csrf.disable())
//
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//
//                .authorizeHttpRequests(auth -> auth
//                        // ✅ VERY IMPORTANT (FIX)
//                        .requestMatchers("/api/auth/**").permitAll()
//
//                        .requestMatchers("/api/cars/**").permitAll()
//                        .requestMatchers("/api/users/**").permitAll()
//
//                        .anyRequest().authenticated()
//                )
//
//                // ✅ ADD JWT FILTER
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    // 🌐 CORS CONFIG
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//
//        CorsConfiguration config = new CorsConfiguration();
//
//        config.setAllowedOrigins(List.of("http://localhost:5173"));
//        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        config.setAllowedHeaders(List.of("*"));
//        config.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//
//        return source;
//    }
//}