package io.github.SavioRomario10.LibraryApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.web.SecurityFilterChain;
import io.github.SavioRomario10.LibraryApi.security.LoginSocialSuccessHandler;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSocialSuccessHandler socialSuccessHandler) throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(Customizer.withDefaults())
        .formLogin(configurer -> 
           configurer.loginPage("/login"))
        .authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/login/**").permitAll();
            authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();

            authorize.anyRequest().authenticated();
          }
        )
        .oauth2Login(oauth2 ->{
          oauth2
            .loginPage("/login")
            .successHandler(socialSuccessHandler);
        })
        .oauth2ResourceServer(
          oauth2Rs -> oauth2Rs.jwt(Customizer.withDefaults()))
        .build();
  } 

  @Bean 
  public GrantedAuthorityDefaults grantedAuthorityDefaults() {
    return new GrantedAuthorityDefaults(""); 
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {

    var authorityConverter = new JwtGrantedAuthoritiesConverter();
    authorityConverter.setAuthorityPrefix("");

    var converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(authorityConverter);

    return converter;
  }
}