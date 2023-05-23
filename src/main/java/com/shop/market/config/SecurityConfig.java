package com.shop.market.config;

import com.shop.market.config.Filter.JwtAuthenticationFilter;
import com.shop.market.config.Oauth.CookieProvider;
import com.shop.market.config.Oauth.OAuth2Service;
import com.shop.market.config.Oauth.Oauth2SuccessHandler;
import com.shop.market.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final OAuth2Service oAuth2Service;
    private final Oauth2SuccessHandler oauth2SuccessHandler;
    private final TokenProvider tokenProvider;
    private final CookieProvider cookieProvider;

    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations()); //정적 리소스 시큐리티 적용 무시
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.cors().disable();
        http.csrf().disable();
        http.httpBasic().disable();
        http.formLogin().disable();

        http.logout()
                .logoutSuccessUrl("/");

        // 세션 생성 x, 기존 세션 사용 x (jwt 사용시)
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(new JwtAuthenticationFilter(tokenProvider,cookieProvider),
                UsernamePasswordAuthenticationFilter.class);

//        http.apply(new MycustomDsl(tokenProvider));

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/","/login/**")
                .permitAll()
                .requestMatchers("/itemV/**", "/postV/**", "/jpa/**", "/home/**")
                .hasRole("USER")
                .requestMatchers("/item/**")
                .hasAnyRole("ADMIN","MANAGER")
                .requestMatchers("/jwt/**").permitAll()
                .anyRequest().authenticated()
        );

        http.oauth2Login()
                .loginPage("/login/login")
                .successHandler(oauth2SuccessHandler)
                .userInfoEndpoint()
                .userService(oAuth2Service);

        return http.build();
    }

    public class MycustomDsl extends AbstractHttpConfigurer<MycustomDsl, HttpSecurity> {
        private TokenProvider tokenProvider;
        public MycustomDsl(TokenProvider tokenProvider){
            this.tokenProvider = tokenProvider;
        }

        @Override
        public void configure(HttpSecurity http) throws Exception{
            http.addFilterBefore(new JwtAuthenticationFilter(tokenProvider,cookieProvider),UsernamePasswordAuthenticationFilter.class);
        }
    }
}
