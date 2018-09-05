package com.shoppingcart.security.core;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.shoppingcart.UrlHandler.LoginSuccessUrlHandler;
import com.shoppingcart.UrlHandler.MyAccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	DataSource dataSource;
	@Autowired
	LoginSuccessUrlHandler loginSuccessUrlHandler;
	@Autowired
	MyAccessDeniedHandler myAccessDeniedHandler;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		
	  auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
			"select username,password, active from userdetails where username=?")
		.authoritiesByUsernameQuery(
			"select username,userrole from userdetails where username=?");
	}	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		System.out.println(" in configure security config........");
	  http.authorizeRequests()
	  	//.antMatchers("/").permitAll()
		.antMatchers("/admin**").access("hasRole('MANAGER')")
		.antMatchers("/getOrderList.html").access("hasRole('MANAGER')")
		.antMatchers("/createProduct**").access("hasRole('MANAGER')")
		.and()
		  .formLogin().loginPage("/login").failureUrl("/login?error")
		  .usernameParameter("username").passwordParameter("password")
		  .successHandler(loginSuccessUrlHandler)
		.and()
		  .logout().logoutSuccessUrl("/login?logout")
		.and()
//		  .exceptionHandling().accessDeniedHandler(myAccessDeniedHandler)
		.exceptionHandling().accessDeniedPage("/Access_Denied")
		.and()
		  .csrf()
		.and()
		.rememberMe().key("uniqueAndSecret").tokenValiditySeconds(120);
	  	
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}
}
