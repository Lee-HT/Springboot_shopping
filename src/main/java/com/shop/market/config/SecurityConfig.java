package com.shop.market.config;

import com.shop.market.config.Filter.CsrfCookieFilter;
import com.shop.market.config.Filter.JwtAuthenticationFilter;
import com.shop.market.config.Cookie.CookieProvider;
import com.shop.market.config.Oauth.LogoutSuccessHandler;
import com.shop.market.config.Oauth.OAuth2Service;
import com.shop.market.config.Oauth.Oauth2SuccessHandler;
import com.shop.market.config.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final OAuth2Service oAuth2Service;
    private final Oauth2SuccessHandler oauth2SuccessHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;
    private final TokenProvider tokenProvider;
    private final CookieProvider cookieProvider;

    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring() // 시큐리티 적용 무시
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations()
                ); //정적 리소스
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.cors().disable();
        http.csrf().disable();
        http.httpBasic().disable();
        http.formLogin().disable();

        http.logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .logoutSuccessUrl("/");

        // 세션 생성 x, 기존 세션 사용 x (jwt 사용시)
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(new JwtAuthenticationFilter(tokenProvider, cookieProvider),
                UsernamePasswordAuthenticationFilter.class);

//        http.apply(new MycustomDsl(tokenProvider));

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login/**").permitAll()
                .requestMatchers("/jwt/**").permitAll()
                .requestMatchers("/itemV/**", "/postV/**", "/jpa/**", "/home/**")
                .hasRole("USER")
                .requestMatchers("/item/**")
                .hasAnyRole("ADMIN", "MANAGER")

                .anyRequest().authenticated()
        );

        // Oauth2
        http.oauth2Login()
                .loginPage("/login/login")
                .successHandler(oauth2SuccessHandler)
                .userInfoEndpoint()
                .userService(oAuth2Service);

        // CSRF
        /*
        CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http.csrf((csrf) -> csrf
                .csrfTokenRepository(tokenRepository)
                .csrfTokenRequestHandler(requestHandler)
        );
        http.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);
         */

        return http.build();
    }

    public class MycustomDsl extends AbstractHttpConfigurer<MycustomDsl, HttpSecurity> {
        private TokenProvider tokenProvider;
        public MycustomDsl(TokenProvider tokenProvider) {
            this.tokenProvider = tokenProvider;
        }
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.addFilterBefore(new JwtAuthenticationFilter(tokenProvider, cookieProvider),
                    UsernamePasswordAuthenticationFilter.class);
        }
    }
}
