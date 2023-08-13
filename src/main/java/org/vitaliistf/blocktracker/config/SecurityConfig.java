package org.vitaliistf.blocktracker.config;

import lombok.AllArgsConstructor;
import org.vitaliistf.blocktracker.service.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_MATCHERS = {
            "/css/**",
            "/js/**",
            "/img/**",
            "/login",
            "/signup",
            "/help",
            "/process_recovery",
            "/confirm_recovery",
            "/confirm",
            "/forgot_password",
            "/error",
            "/",
            "http://localhost:8080/oauth2/google/callback"
    };

    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //In production this should be enabled
                .authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated()

                .and()

                .oauth2Login().loginPage("/login")
                .defaultSuccessUrl("/oauth2/google/callback", true)
                .failureUrl("/login?error")

                .and()

                .formLogin().loginPage("/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")

                .and()

                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
