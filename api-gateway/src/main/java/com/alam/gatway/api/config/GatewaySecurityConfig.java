package com.alam.gatway.api.config;

import com.alam.gatway.api.security.CustomAccessDeniedHandler;
import com.alam.gatway.api.security.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class GatewaySecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity,
                                                         CustomAuthenticationEntryPoint entryPoint,
                                                         CustomAccessDeniedHandler accessDeniedHandler ) {
        return httpSecurity
                // ❌ CSRF disable (API ke liye required)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                // ❌ Session disable (stateless system)
                //.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())

                // 🔐 Route-based security
                .authorizeExchange((authorize) -> authorize
                        .pathMatchers("/api/v1/auth/**").permitAll()   // public APIs
                        .pathMatchers("/actuator/**").permitAll()      // health check
                        .anyExchange().authenticated()
                )
                .oauth2Login(Customizer.withDefaults())
                .exceptionHandling(ex->
                        ex.authenticationEntryPoint(entryPoint)
                                .accessDeniedHandler(accessDeniedHandler)
                )
                // 🔑 JWT validation
                .oauth2ResourceServer(resourceServer -> resourceServer
                        .jwt(Customizer.withDefaults())
                        .authenticationEntryPoint(entryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .build();
    }
}
