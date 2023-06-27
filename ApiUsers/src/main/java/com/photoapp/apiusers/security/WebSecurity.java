package com.photoapp.apiusers.security;

import com.photoapp.apiusers.service.impl.UsersService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurity {

	private Environment environment;

	private UsersService usersService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public WebSecurity(UsersService usersService,
			BCryptPasswordEncoder bCryptPasswordEncoder,Environment environment) {
		this.usersService = usersService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.environment = environment;
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
				new AuthenticationFilter(usersService, authenticationManager,environment);
		authenticationFilter.setFilterProcessesUrl("/users/login");

		http.csrf().disable();

		http.authorizeHttpRequests()
				.requestMatchers(HttpMethod.POST, "/users").access(
						new WebExpressionAuthorizationManager("hasIpAddress('"+environment.getProperty("gateway.ip")+"')"))
				.requestMatchers(HttpMethod.GET, "/users/**").access(
						new WebExpressionAuthorizationManager("hasIpAddress('"+environment.getProperty("gateway.ip")+"')"))
				.requestMatchers(HttpMethod.GET, "/users/check").access(
						new WebExpressionAuthorizationManager("hasIpAddress('"+environment.getProperty("gateway.ip")+"')"))
				.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
				// .and()
				// .exceptionHandling()
				// .authenticationEntryPoint(authenticationEntryPoint())
				// .accessDeniedHandler(accessDeniedHandler())
				.and()
				.addFilter(authenticationFilter)
				.authenticationManager(authenticationManager)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.headers().frameOptions().disable();
		
		return http.build();
		
	}


	private AuthenticationEntryPoint authenticationEntryPoint() {
		return (request, response, authException) -> {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
		};
	}

	private AccessDeniedHandler accessDeniedHandler() {
		return (request, response, accessDeniedException) -> {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
		};
	}

}
