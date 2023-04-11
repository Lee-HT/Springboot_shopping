package com.shop.market.config;

import com.shop.market.config.Service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuthService oAuthService;

    @Bean
    public PasswordEncoder PasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.cors().disable();
        http.csrf().disable();
        http.httpBasic().disable();
        http.formLogin().disable();

        http.logout().logoutSuccessUrl("/");

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests((authz)-> authz
                .requestMatchers("/").permitAll()
                .anyRequest().permitAll());
        http.oauth2Login().userInfoEndpoint().userService(oAuthService);

        return http.build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity>{
        @Override
        public void configure(HttpSecurity http) throws Exception{
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        }
    }

}
