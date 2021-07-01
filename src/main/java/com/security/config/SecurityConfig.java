package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.security.service.UserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}	
	
	@Autowired
	UserDetailService userDetailService;
	
	@Autowired
	BCryptPasswordEncoder pe;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(getPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Tat csft(ngan chan cac request gia lap) va tat cors(ngan chan chia se tai nguyen) de tu cau hinh
		http.csrf().disable().cors().disable();
		
		// phan quyen su dung
		http.authorizeRequests()
			.antMatchers("/home/admin").hasRole("ADMIN")
			.antMatchers("/home/user", "/rest/authorities/**").hasAnyRole("ADMIN", "USER")
			.antMatchers("/home/authenticated").authenticated()
			.anyRequest().permitAll();
		http.httpBasic();
		// vao ko dung vai tro
		http.exceptionHandling()
			.accessDeniedPage("/home/access/denied");
		
		//giao dien dang nhap
		http.formLogin()
			.loginPage("/auth/login/form")
			.loginProcessingUrl("/auth/login")
			.defaultSuccessUrl("/home/index")
			.failureUrl("/auth/login?error=true")
			.usernameParameter("username")
			.passwordParameter("password");
		
		//dang nhap bang social
		http.oauth2Login()
			.loginPage("/auth/login/form")
			.defaultSuccessUrl("/oauth2/login/success", true)
			.failureUrl("/auth/login/error")
			.authorizationEndpoint()
				.baseUri("/oauth2/authorization");
		
		http.rememberMe()
			.rememberMeParameter("remember");
		
		//logout
		http.logout().logoutUrl("/auth/logout")
			.logoutSuccessUrl("/auth/logout/success");
	}
	
	
}
