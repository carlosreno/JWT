package DemandasDm.api.rest.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import DemandasDm.api.rest.servicos.ImplementacaoUserDetaisService;
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebConfiSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ImplementacaoUserDetaisService implementacaoUserDetaisService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
 		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		
		.disable().authorizeRequests().antMatchers("/").permitAll()
		
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		
		.antMatchers("/index").permitAll()
		
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		
		.and().addFilterBefore(new JWTLoginFilter("/login",authenticationManager()) , UsernamePasswordAuthenticationFilter.class)
		
		.addFilterBefore(new JwtApiAutenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
	auth.userDetailsService(implementacaoUserDetaisService).passwordEncoder(
			new BCryptPasswordEncoder());
	
	}
	
}
