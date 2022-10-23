package com.blubank.doctorappointment.config;

import com.blubank.doctorappointment.web.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@EnableWebSecurity(debug = false)
public class SecurityConfig {

    public static final String[] H2_CONSOLE = {
            "/h2-console/**"
    };

    public static final String[] SWAGGER_UI_V2 = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    private static final String[] Swagger_UI_v3_OpenAPI = {
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(
            HttpSecurity http,
            JwtTokenFilter jwtTokenFilter
    ) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(authorizeRequests -> {
                    authorizeRequests.antMatchers(HttpMethod.POST, "/login", "/api/v1/users").permitAll();
                    authorizeRequests.antMatchers(HttpMethod.GET, "/api/v1/appointments").hasRole("DOCTOR");
                    authorizeRequests.antMatchers(HttpMethod.POST, "/api/v1/open-appointments").hasRole("DOCTOR");
                    authorizeRequests.antMatchers(HttpMethod.DELETE, "/api/v1/open-appointments").hasRole("DOCTOR");
                    authorizeRequests.antMatchers(HttpMethod.GET, "/api/v1/open-appointments").hasRole("PATIENT");
                    authorizeRequests.antMatchers(HttpMethod.PUT, "/api/v1/open-appointments").hasRole("PATIENT");
                    authorizeRequests.antMatchers(HttpMethod.GET, "/api/v1/taken-appointments").hasRole("PATIENT");
                    authorizeRequests.anyRequest().authenticated();
//                    authorizeRequests.anyRequest().permitAll();
                })
                .exceptionHandling().authenticationEntryPoint((httpServletRequest, httpServletResponse, e) ->
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage())
        ).and()
                .httpBasic().disable()
                .csrf().disable()
                .headers().frameOptions().disable();
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers(SWAGGER_UI_V2)
                .antMatchers(Swagger_UI_v3_OpenAPI)
                .antMatchers(H2_CONSOLE);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
