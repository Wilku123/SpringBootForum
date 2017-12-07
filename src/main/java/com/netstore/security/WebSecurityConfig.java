package com.netstore.security;

import com.netstore.api.mobile.security.*;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * Created by Master on 2017-04-22.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Value("${spring.queries.token-query}")
    private String credentialsQuery;

    @Value("${spring.queries.rest-roles-query}")
    private String restRolesQuery;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private BasicAuthPoint basicAuthPoint;


    @Autowired
    private MySavedRequestAwareAuthenticationSuccessHandler mySavedRequestAwareAuthenticationSuccessHandler;



    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .exceptionHandling()
                .and()
                .authorizeRequests()
//                .antMatchers("/**").permitAll() // TODO USUN TO!
                .antMatchers("/webjars/**","/css/**","/js/**","/icons/**","/built/**").permitAll()
                .antMatchers("/react/register","/react/activate","/react/forgotPassword","/react/changePass").permitAll()
                .antMatchers("/registerrest","/register","/validateToken").permitAll()
                .antMatchers("/reg","/forgot","/resetPass").permitAll()
                .antMatchers("/login","/loginReact").permitAll()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/react/main/**","/main/**").authenticated()
                .antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
                .authenticated().and().csrf().disable()
                .formLogin()
                .loginPage("/login").failureUrl("/login")
//                .loginProcessingUrl("/loginReact")
                .successHandler(successHandler())
                .failureHandler(myFailureHandler())
                .defaultSuccessUrl("/main/circle",true)
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and().exceptionHandling()
                .accessDeniedPage("/access-denied")
                .and()
                .httpBasic()
                .authenticationEntryPoint(basicAuthPoint);
//        http
//                .authorizeRequests()
//                .antMatchers("/api/**").authenticated()
//                .and().csrf().disable().formLogin()
//                .loginPage("/api/login")
//                .usernameParameter("token")
//                .passwordParameter("pin")
//                .and()
//                .httpBasic()
//                .authenticationEntryPoint(restAuthenticationEntryPoint);






//        http
//                .authorizeRequests()
//                .antMatchers("/webjars/**","/css/**","/js/**","/icons/**", "/","/circlerest","/greeting","/processAddCircleForm").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                //.successHandler(successHandler())
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/")
//                .permitAll()
//                .and()
//                .exceptionHandling().accessDeniedPage("/");

//        http
//                .csrf()
//                .disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(restAuthenticationEntryPoint)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/api/**").authenticated()
//                .and()
//                .formLogin()
//                .successHandler(mySuccessHandler())
//                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
//                .and()
//                .logout();
//                .and
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic()
//                .authenticationEntryPoint(authEntryPoint);

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, AuthenticationManagerBuilder rest) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("1").password("1").roles("USER");

        auth
                .jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder());

        rest
                .jdbcAuthentication()
                .usersByUsernameQuery(credentialsQuery)
                .authoritiesByUsernameQuery(restRolesQuery)
                .dataSource(dataSource);

//        rest
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(bCryptPasswordEncoder);
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean()
            throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setUseReferer(true);
        return handler;
    }
    public MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler(){
        return new MySavedRequestAwareAuthenticationSuccessHandler();
    }
    public SimpleUrlAuthenticationFailureHandler myFailureHandler(){
        return new SimpleUrlAuthenticationFailureHandler();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

