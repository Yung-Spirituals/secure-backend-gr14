package no.ntnu.secureBackendGr14.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Creates AuthenticationManager - set up authentication type
 * The @EnableWebSecurity tells that this ia a class for configuring web
 * security
 * The @EnableGlobalMethodSecurity is needed so that each endpoint can specify
 * which role it requires
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * A service providing our users from the database
     */
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * This method will be called automatically by the framework to find out what
     * authentication to use.
     * Here we tell that we want to load users from a database
     *
     * @param auth Authentication builder
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    /**
     * Configure the authorization rules
     * @param http HTTP Security object
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Allow JWT authentication
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/admin").hasRole("ADMIN")
                .antMatchers("/api/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/").permitAll()
                .antMatchers("/product/").hasRole("ADMIN")
                .antMatchers("/product/get-products").permitAll()
                .antMatchers("/product/add-product").hasRole("ADMIN")
                .antMatchers("/product/update-product").hasRole("ADMIN")
                .antMatchers("/product/delete-product").hasRole("ADMIN")
                .antMatchers("/cart/get-cart").permitAll()
                .antMatchers("/cart/update-cart").permitAll()
                .antMatchers("/cart/delete-cart").permitAll()
                .antMatchers("/cart/empty-cart").permitAll()
                .antMatchers("/order/all-orders").hasRole("ADMIN")
                .antMatchers("/order/new-order").permitAll()
                .antMatchers("/order/delete-order").hasRole("ADMIN")
                .antMatchers("/order/delete-all-orders").hasRole("ADMIN")
                .antMatchers("/order/process").hasRole("ADMIN")
                .antMatchers("/order/revert-processed-order").hasRole("ADMIN")
                .antMatchers("/authenticate/register").anonymous()
                .antMatchers("/authenticate/login").anonymous()
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Enable our JWT authentication filter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * This is needed since Spring Boot 2.0
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * This method is called to decide what encryption to use for password checking
     *
     * @return The password encryptor
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}