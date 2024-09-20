package com.eni.pizzaWebsite.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class securityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT email, password,1 FROM user WHERE email=?");

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT u.email, ur.user_role FROM user as u INNER JOIN user_role as ur ON u.id_role=ur.id_role WHERE u.email=?");

        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize

                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/customLogin").permitAll()
                                .requestMatchers("/authForm").hasAuthority("ROLE_MANAGER")
                                .requestMatchers("/vendor/**").permitAll()
                                .requestMatchers("/images/**").permitAll()
                                .requestMatchers("/css/**").permitAll()

                                .requestMatchers("/logout").authenticated()
                                .requestMatchers("/user").authenticated()
                                .requestMatchers("/").authenticated()

                                .requestMatchers("/products-list").hasAnyAuthority("ROLE_MANAGER", "ROLE_PIZZAMAKER")
//                        .requestMatchers("/orders-list").hasAnyAuthority("ROLE_MANAGER", "ROLE_PIZZAMAKER", "ROLE_DELIVERYPERSON")
                                .requestMatchers(HttpMethod.GET, "/orders-list/{id_state}").hasAnyAuthority("ROLE_MANAGER", "ROLE_PIZZAMAKER", "ROLE_DELIVERYPERSON")
                                .requestMatchers("/product-form").hasAuthority("ROLE_MANAGER")
                                .requestMatchers("/product-form/{id}").hasAuthority("ROLE_MANAGER")
                                .requestMatchers("/products-list/delete/{id}").hasAuthority("ROLE_MANAGER")
                                .requestMatchers("/create-order").hasAnyAuthority("ROLE_MANAGER", "ROLE_PIZZAMAKER")
                                .requestMatchers("/orders-list/delete/{id_order}").hasAuthority("ROLE_MANAGER")
                                .requestMatchers("/clients-list").hasAnyAuthority("ROLE_MANAGER", "ROLE_PIZZAMAKER")
                                .requestMatchers("/client-form").hasAnyAuthority("ROLE_MANAGER", "ROLE_PIZZAMAKER")
                                .requestMatchers("/cart/remove/{id}").hasAuthority("ROLE_MANAGER")
                                .requestMatchers("/cart/clear").hasAuthority("ROLE_MANAGER")
                                .requestMatchers(HttpMethod.POST, "/update-order-delivery-date/{id_order}").permitAll()
                                .requestMatchers("/update-order-state/{id_order}").permitAll()
                                .requestMatchers(HttpMethod.POST,"/orders-list").permitAll()
                                .requestMatchers("/orders-edit/{id_order}").hasAnyAuthority("ROLE_MANAGER", "ROLE_PIZZAMAKER")


                                .requestMatchers("/add-orderDetail-to-order").permitAll()
                                .requestMatchers("/remove-product-from-order").permitAll()

                                .anyRequest().denyAll()
                );

        //Pour utiliser le fonctionnement de login de Spring Security par défaut

        //http.formLogin(Customizer.withDefaults());


        //Route personnalisée définie dans notre Controller

        http.formLogin(form ->
                form.loginPage("/customLogin").defaultSuccessUrl("/")
        );


        HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.ALL));

        http.logout((logout) -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/customLogin")
                .addLogoutHandler(clearSiteData)

        );


        // ...

        return http.build();
    }
}
