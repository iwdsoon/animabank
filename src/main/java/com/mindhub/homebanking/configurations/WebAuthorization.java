package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
class WebAuthorization extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/admin/**").hasAuthority("ADMIN")
				.antMatchers("/web/manager.html").hasAuthority("ADMIN")
				.antMatchers("/rest/**").hasAuthority("ADMIN")
				.antMatchers("/h2-console/**").hasAuthority("ADMIN")
				.antMatchers(HttpMethod.POST,"/api/clients").permitAll()
				.antMatchers(HttpMethod.GET, "/api/clients").hasAuthority("ADMIN")
				.antMatchers(HttpMethod.POST,"/api/createloan").hasAuthority("ADMIN")
				.antMatchers(HttpMethod.POST,"/api/clients/current/accounts").hasAuthority("CLIENT")
				.antMatchers(HttpMethod.GET,"/api/clients/current/accounts/**").hasAuthority("CLIENT")
				.antMatchers(HttpMethod.POST, "/api/clients/current/cards").hasAuthority("CLIENT")
				.antMatchers(HttpMethod.GET,"/api/clients/current/cards").hasAuthority("CLIENT")
				.antMatchers(HttpMethod.POST, "/api/transactions").hasAuthority("CLIENT")
				.antMatchers(HttpMethod.POST, "/api/loans").hasAuthority("CLIENT")
				.antMatchers(HttpMethod.GET,"/api/loans").hasAuthority("CLIENT")
				.antMatchers(HttpMethod.PATCH,"/api/clients/current/cards").hasAuthority("CLIENT")
				.antMatchers(HttpMethod.GET,"/api/client_loans").hasAuthority("CLIENT")
				.antMatchers(HttpMethod.PATCH,"/api/clients/current/accounts").hasAuthority("CLIENT")
				.antMatchers(HttpMethod.GET,"/api/clients/current/accounts/transactions/latest").hasAuthority("CLIENT")
				.antMatchers("/api/accounts/**").hasAuthority("CLIENT")
				.antMatchers("/api/clients/current").hasAuthority("CLIENT")
				.antMatchers("/api/clients/current/accounts").hasAuthority("CLIENT")
				.antMatchers("/api/**").hasAuthority("ADMIN")
				.antMatchers("/web/accounts.html").hasAuthority("CLIENT")
				.antMatchers("/web/loan-application.html").hasAuthority("CLIENT")
				.antMatchers("/web/dashboard.html").hasAuthority("CLIENT")
				.antMatchers("/web/account.html").hasAuthority("CLIENT")
				.antMatchers("/web/cards.html").hasAuthority("CLIENT")
				.antMatchers("/web/create-cards.html").hasAuthority("CLIENT")
				.antMatchers("/web/transfers.html").hasAuthority("CLIENT")
				.antMatchers("/web/index.html").permitAll()
				.antMatchers("/web/login.html").permitAll()
				.antMatchers("/web/assets/**").permitAll()
				.antMatchers("/web/scripts/**").permitAll()
				.antMatchers("/web/styles/**").permitAll()
				.antMatchers("/**").permitAll();

		http.formLogin()
				.usernameParameter("email")
				.passwordParameter("password")
				.loginProcessingUrl("/api/login")
				.loginPage("/web/login.html")
				.successForwardUrl("/web/accounts.html")
				.defaultSuccessUrl("/web/accounts.html",true)
				.failureUrl("/login.html?error=true");

		http.logout().logoutUrl("/api/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();
		//disabling frameOptions so h2-console can be accessed
		http.headers().frameOptions().disable();
		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {

		HttpSession session = request.getSession(false);

		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}




}