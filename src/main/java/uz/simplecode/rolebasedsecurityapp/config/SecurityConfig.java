package uz.simplecode.rolebasedsecurityapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//jsr250Enabled = true -> @RolesAllowed("ADMIN"), securedEnabled = true -> @Secured("ADMIN"), prePostEnabled = true -> @PreAuthorize("hasRole('ADMIN')"
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final DataSource dataSource;

  public SecurityConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
            .csrf().disable() //disable qilinmasa POST,PUT,DELETE,PATCH 403 beradi.
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic();
  }

//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {  //inMemory
//    auth
//            .inMemoryAuthentication()
//            .withUser("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN").authorities("GET_ALL_GROUP","GET_GROUP","SAVE_GROUP","EDIT_GROUP","DELETE_GROUP")
//            .and()
//            .withUser("user").password(passwordEncoder().encode("user123")).authorities("GET_ALL_GROUP","GET_GROUP");
//  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {   // inDataBase
    auth
            .jdbcAuthentication()
            .passwordEncoder(passwordEncoder())
            .dataSource(dataSource)
            .usersByUsernameQuery("select username, password, enabled from public.users where username = ?")
            .authoritiesByUsernameQuery("select username, role_name  from public.roles where username = ?");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }
}
