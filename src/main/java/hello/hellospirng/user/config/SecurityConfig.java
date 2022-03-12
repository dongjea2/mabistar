package hello.hellospirng.user.config;

import hello.hellospirng.user.jwt.JwtAccessDeniedHandler;
import hello.hellospirng.user.jwt.JwtAuthenticationEntryPoint;
import hello.hellospirng.user.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(TokenProvider tokenProvider, JwtAccessDeniedHandler deniedHandler, JwtAuthenticationEntryPoint entryPoint){
        this.tokenProvider = tokenProvider;
        this.jwtAccessDeniedHandler = deniedHandler;
        this.jwtAuthenticationEntryPoint = entryPoint;
    }



    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //토큰방식 사용함으로 csrf 비활성
                .cors().disable()
                .csrf().disable()

                //커스텀한 핸들러, 앤트리 포인트 사용
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .formLogin().disable()
                .headers().frameOptions().disable()

                //세션 사용안함
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                //

                //
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/auth").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/signUp").permitAll()
                .anyRequest().authenticated()

                //JwtSecurityConfig 등록
                // (jwtFilter를 addFilterBefore로 등록해 놨던거)
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));

    }
}
