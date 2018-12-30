package org.myaldoc.security.core.configuration;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @Project MYALDOC_PORJECT
 * @Author Henri Joel SEDJAME
 * @Date 30/12/2018
 * @Class purposes : .......
 */

@Configuration
@Order(0)
@EnableOAuth2Sso
public class OAuth2SSOConfiguration extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/token").permitAll()
                .antMatchers("/auth/**", "/login").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("http://localhost:9000/auth/exit");
    }
}
