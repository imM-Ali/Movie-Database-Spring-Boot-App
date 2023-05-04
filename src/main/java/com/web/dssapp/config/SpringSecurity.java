package com.web.dssapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		return provider;
	}

	 @Bean
	
	    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 
		 
	        http.authorizeHttpRequests((authz) -> {
	            try {
	                authz.requestMatchers("/signup","/login").permitAll()
	                .requestMatchers("/roles").hasAuthority("admin")
	                .requestMatchers("users/**").hasAuthority("admin")
					.requestMatchers("/editmovie/**").hasAuthority("admin")
					.requestMatchers("/deletemovie/**").hasAuthority("admin")
					.requestMatchers("/users/**").hasAuthority("admin")
					.requestMatchers("roles").hasAuthority("admin")
	                    .anyRequest().authenticated()
	                    .and().exceptionHandling().accessDeniedPage("/noaccess").and()	                    
	                    .formLogin(login -> login
	                        .loginPage("/login")
	                        .defaultSuccessUrl("/movies/1", true))
	                    .logout(logout -> logout
	                    		 .invalidateHttpSession(true)) 
	                    .httpBasic(withDefaults());
	            } catch (Exception e) {
	                System.out.println(e.getMessage());
	            }
	        });

	        return http.build();
	    }	
	 
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}
