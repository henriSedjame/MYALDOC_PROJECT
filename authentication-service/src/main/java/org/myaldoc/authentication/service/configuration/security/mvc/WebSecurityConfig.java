package org.myaldoc.authentication.service.configuration.security.mvc;

import lombok.extern.slf4j.Slf4j;
import org.myaldoc.authentication.service.configuration.security.mvc.handlers.ConnexionFailureHandler;
import org.myaldoc.authentication.service.configuration.security.mvc.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Profile("security")
@Configuration
@EnableResourceServer
@Order(1)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  CustomUserDetailsService service;
  @Autowired
  BCryptPasswordEncoder passwordEncoder;
  @Autowired
  ConnexionFailureHandler failureHandler;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
            .userDetailsService(service)
            .passwordEncoder(passwordEncoder);

  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            /** Désactiver l'auth basic **/
            .and()
            .httpBasic().disable()

            /** Désactiver la gestion du csrf par Spring **/
            .csrf().disable()

            /** Définir les droits d'accès **/
            .requestMatchers()
            .antMatchers("/login", "/oauth/token", /** "/oauth/authorize", */"/exit", "/activation**", "/user/create", "/user/delete/**", "/user/activate/**")
            .and()
            .authorizeRequests()
            .antMatchers("/oauth/token", "/login", "/user/create", "/user/delete/**", "/user/activate/**", "/activation**").permitAll()
            .anyRequest().authenticated()

            /** Gérer la connexion **/
            .and().formLogin().disable()
            //.loginPage("/login").permitAll()
            //.failureHandler(this.failureHandler)

            /** Gérer la déconnexion **/
            // .and()
            .logout().logoutUrl("/logout")
            .permitAll();
  }

  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }
}
