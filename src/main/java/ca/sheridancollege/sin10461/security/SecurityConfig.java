package ca.sheridancollege.sin10461.security;



import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	DataSource dataSource;

	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder());
	}
	
	
	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager()throws Exception {
		JdbcUserDetailsManager jdbcUserDetailsManager=new JdbcUserDetailsManager();
		jdbcUserDetailsManager.setDataSource(dataSource);
		
		List<GrantedAuthority> authUser = new ArrayList<GrantedAuthority>();
		authUser.add(new SimpleGrantedAuthority("ROLE_USER"));
		List<GrantedAuthority> authManager = new ArrayList<GrantedAuthority>();
		authManager.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		List<GrantedAuthority> authBoth = new ArrayList<GrantedAuthority>();
		authBoth.add(new SimpleGrantedAuthority("ROLE_USER"));
		authBoth.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		String encodedPassword = new BCryptPasswordEncoder().encode("pass");
		User u1 = new User("harpreet", encodedPassword, authUser);
		User u2 = new User("John", encodedPassword, authManager);
		User u3 = new User("Anjali", encodedPassword, authBoth);
		jdbcUserDetailsManager.createUser(u1);
		jdbcUserDetailsManager.createUser(u2);
		jdbcUserDetailsManager.createUser(u3);
		
		
		return jdbcUserDetailsManager;
		
	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		web
		.ignoring().antMatchers("/h2-console/**");
		
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception{
		
		http.requiresChannel().anyRequest().requiresSecure().and()
		.authorizeRequests().antMatchers("/").permitAll()
		.and()
		.authorizeRequests().antMatchers("/register").permitAll()
		.and()
		.authorizeRequests().antMatchers("/calculation").hasAnyRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers("/admin").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login").permitAll()
		.and().logout().invalidateHttpSession(true).clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login?logout").permitAll();
	}
}
