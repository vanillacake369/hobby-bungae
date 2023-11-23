package com.example.hobbybungae.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import com.example.hobbybungae.security.JwtAuthenticationFilter;
import com.example.hobbybungae.security.JwtAuthorizationFilter;
import com.example.hobbybungae.security.JwtUtil;
import com.example.hobbybungae.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
            authorizeHttpRequests
                .requestMatchers(HttpMethod.GET, "v1/users/profile/{$user-id}")
                .authenticated() // 프로필 조회 요청 인증 요망
                .requestMatchers(HttpMethod.GET, "v1/users/profile")
                .authenticated() // 프로필 조회 요청 인증 요망
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll() // resources 접근 허용 설정
                .requestMatchers(
                    antMatcher(HttpMethod.GET, "/hobby-bungae/v1/hobbies/**")
                ).permitAll()  // 글 단건,전체 조회 모두 접근 허용
                .requestMatchers("/hobby-bungae/v1/users/**").permitAll() // '/api/user/'로 시작하는 요청 모두 접근 허가
                .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );

        http.formLogin((formLogin) ->
            formLogin
                .loginPage("/hobby-bungae/v1/users/signin").permitAll()
        );

        // 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

