package br.edu.ifsul.tsi.vagas.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;
//    @Autowired
//    private UnauthorizedHandler unauthorizedHandler;
//    @Autowired
//    private AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //Configuração para Basic Authentication
        http.authorizeRequests()
            .anyRequest().authenticated()
            .and().httpBasic()
            .and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);


//        //autenticação em memória
//        auth
//            // enable in memory based authentication with a user named "user" and "admin"
//            .inMemoryAuthentication().passwordEncoder(encoder)
//                .withUser("user").password(encoder.encode("user")).roles("USER")
//                .and()
//                .withUser("admin").password(encoder.encode("admin")).roles("USER", "ADMIN");
    }
}
