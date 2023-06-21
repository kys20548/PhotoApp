package com.photoapp.apiusers.security;

import com.photoapp.apiusers.service.impl.UsersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurity {

	@Value("${gateway.ip}")
	String gatewayIp;

	@Value("${token.secret}")
	String token;
	@Value("${token.expiration_time}")
	String tokenExpireTime;

	private UsersService usersService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public WebSecurity(UsersService usersService,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.usersService = usersService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

		// Configure AuthenticationManagerBuilder
		AuthenticationManagerBuilder authenticationManagerBuilder =
				http.getSharedObject(AuthenticationManagerBuilder.class);

		authenticationManagerBuilder.userDetailsService(usersService)
				.passwordEncoder(bCryptPasswordEncoder);

		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

		// Create AuthenticationFilter
		AuthenticationFilter authenticationFilter =
				new AuthenticationFilter(usersService, token,tokenExpireTime, authenticationManager);
		authenticationFilter.setFilterProcessesUrl("/users/login");

		http.csrf().disable();

		http.authorizeHttpRequests()
				.requestMatchers(HttpMethod.POST, "/users").access(
						new WebExpressionAuthorizationManager("hasIpAddress('"+gatewayIp+"')"))
				.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
				.and()
				.addFilter(authenticationFilter)
				.authenticationManager(authenticationManager)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		;
		
		http.headers().frameOptions().disable();
		
		return http.build();
		
	}

}
