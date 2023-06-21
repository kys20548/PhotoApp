package com.photoapp.apiusers.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoapp.apiusers.dto.LoginDTO;
import com.photoapp.apiusers.model.Users;
import com.photoapp.apiusers.service.impl.UsersService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private  UsersService usersService;
	private  String token;
	private  String tokenExpireTime;
	public AuthenticationFilter(UsersService usersService,
			String token,String tokenExpireTime, AuthenticationManager authenticationManager) {
		super(authenticationManager);
		this.usersService = usersService;
		this.token = token;
		this.tokenExpireTime = tokenExpireTime;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {

			LoginDTO creds = new ObjectMapper().readValue(req.getInputStream(), LoginDTO.class);

			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// if user validation is success, then spring will call this function
	// and this method is provided jwt token on the response header
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		String userName = ((User) auth.getPrincipal()).getUsername();
		Users userDetails = usersService.getUserByEmail(userName);
		// String tokenSecret = environment.getProperty("token.secret");
		byte[] secretKeyBytes = Base64.getEncoder().encode(token.getBytes());
		SecretKey secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

		Instant now = Instant.now();

		String token = Jwts.builder().setSubject(userDetails.getId()+"")
				.setExpiration(
						Date.from(now.plusMillis(Long.parseLong(tokenExpireTime))))
				.setIssuedAt(Date.from(now)).signWith(secretKey, SignatureAlgorithm.HS512).compact();
		//
		res.addHeader("token", token);
		res.addHeader("userId", userDetails.getId()+"");
	}
}
