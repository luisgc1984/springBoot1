package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	BCryptPasswordEncoder bCryptPasswordEncoder;
	String[] resources = new String[] {"/include/**", "/css/**", "/js/**", "/img/**", "/icons/**", "/layer/**"};
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.authorizeRequests()
		.antMatchers(resources).permitAll()
		.antMatchers("/", "/index", "/registration", "/rememberPassword").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()
			.loginPage("/login")
			.permitAll()
			.defaultSuccessUrl("/userForm")
			.failureUrl("/login?error")
			.usernameParameter("username")
			.passwordParameter("password")
			.and().csrf().disable()
		.logout()
			.permitAll()
			.logoutSuccessUrl("/login?logout");
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		
		return bCryptPasswordEncoder;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}
