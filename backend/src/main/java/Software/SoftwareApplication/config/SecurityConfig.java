package Software.SoftwareApplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests((auth) -> auth //특정 경로 요청
                        .requestMatchers("/api/random", "/api/signup", "/api/signin").permitAll() // requestMatchers 특정 경로에 대한 것 설정
                        // .requestMatchers("/admin").hasRole("ADMIN") // 인가
                        .anyRequest().authenticated() // 나머지 경로 로그인 필수
                );

        return http.build();
    }

}
