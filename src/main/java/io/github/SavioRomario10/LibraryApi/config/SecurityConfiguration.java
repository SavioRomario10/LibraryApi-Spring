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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import io.github.SavioRomario10.LibraryApi.security.CustomUserDetailsService;
import io.github.SavioRomario10.LibraryApi.services.UsuarioService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(Customizer.withDefaults())
//        .formLogin(configurer -> 
//           configurer.loginPage("/login"))
        .formLogin(Customizer.withDefaults())
        .authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/login/**").permitAll();
            authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();

            authorize.anyRequest().authenticated();
          }
        )
        .oauth2Login(Customizer.withDefaults())
        .build();
  } 

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }

//  @Bean
  public UserDetailsService userDetailsService(UsuarioService usuarioService) {
    /**
     * 
    UserDetails user1 = User.builder()
    .username("usuario")
    .password(encoder.encode("123"))
    .roles("USER")
    .build();
    
    UserDetails user2 = User.builder()
    .username("admin")
    .password(encoder.encode("admin"))
    .roles("ADMIN")
    .build();
    
    return new InMemoryUserDetailsMenager(user1, user2)
    */
    return new CustomUserDetailsService(usuarioService);
  }

  @Bean 
  public GrantedAuthorityDefaults grantedAuthorityDefaults() {
    return new GrantedAuthorityDefaults(""); 
  }
}