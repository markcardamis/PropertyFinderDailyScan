package com.majoapps.propertyfinderdailyscan.security;

import com.okta.spring.boot.oauth.Okta;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
public class MultiHttpSecurityConfig {

    @Configuration
    static class OktaOAuth2WebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                .requiresChannel()
                    .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                    .requiresSecure()
                    .and()
                .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .exceptionHandling()
                    .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                    .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET,"/", "/css/**", "/images/**", "/favicon.ico").permitAll()
                    .and()
                .authorizeRequests()
                    .antMatchers("/api/**").authenticated()
                    .and()
                .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                .oauth2Client()
                    .and()
                .oauth2Login()
                    .and()
                .oauth2ResourceServer().jwt();
            Okta.configureResourceServer401ResponseBody(http);
        }
    }

}