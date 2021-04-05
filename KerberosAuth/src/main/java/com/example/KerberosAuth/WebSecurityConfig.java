package com.example.KerberosAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.kerberos.authentication.KerberosAuthenticationProvider;
import org.springframework.security.kerberos.authentication.KerberosServiceAuthenticationProvider;
import org.springframework.security.kerberos.authentication.KerberosServiceRequestToken;
import org.springframework.security.kerberos.authentication.sun.SunJaasKerberosClient;
import org.springframework.security.kerberos.authentication.sun.SunJaasKerberosTicketValidator;
import org.springframework.security.kerberos.client.config.SunJaasKrb5LoginConfig;
import org.springframework.security.kerberos.client.ldap.KerberosLdapContextSource;
import org.springframework.security.kerberos.web.authentication.SpnegoAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import  com.example.KerberosAuth.SpnegoEntry;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
   /* @Value("${app.service-principal}")
    private String servicePrincipal;

    @Value("${app.keytab-location}")
    private String keytabLocation;*/
    
	@Autowired
	SpnegoEntry spnegoEntryPoint;
    
    
	@Override
    protected void configure(HttpSecurity http) throws Exception {
       
		http
        	.exceptionHandling()
        	.authenticationEntryPoint(spnegoEntryPoint)
        .and()
        	.authorizeRequests()
        	.antMatchers("/", "/home").permitAll()
        	.anyRequest().authenticated()
        .and()
        	.formLogin()
        	.loginPage("/login").permitAll()
        .and()
        	.logout()
        	.permitAll()         
        .and()
        	.addFilterBefore(
            spnegoAuthenticationProcessingFilter(authenticationManagerBean()),
            BasicAuthenticationFilter.class);
  
    }
	
	
	@Bean
    public SpnegoAuthenticationProcessingFilter spnegoAuthenticationProcessingFilter(AuthenticationManager authenticationManager) {
        SpnegoAuthenticationProcessingFilter filter =
                new SpnegoAuthenticationProcessingFilter();
        try {
            filter.setAuthenticationManager(authenticationManager);
        } catch (Exception e) {
            System.out.println("Failed to set AuthenticationManager on SpnegoAuthenticationProcessingFilter." + e);
        }
        return filter;
    }
	
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        	auth
                .authenticationProvider(kerberosAuthenticationProvider())
                .authenticationProvider(kerberosServiceAuthenticationProvider());
    }

    @Bean
    public KerberosAuthenticationProvider kerberosAuthenticationProvider() {
        KerberosAuthenticationProvider provider =
                new KerberosAuthenticationProvider();
        SunJaasKerberosClient client = new SunJaasKerberosClient();
        client.setDebug(true);
        provider.setKerberosClient(client);
        provider.setUserDetailsService(dummyUserDetailsService());
        return provider;
    }

       

    @Bean
    public KerberosServiceAuthenticationProvider kerberosServiceAuthenticationProvider() {
        KerberosServiceAuthenticationProvider provider =
                new KerberosServiceAuthenticationProvider();
        provider.setTicketValidator(sunJaasKerberosTicketValidator());
        provider.setUserDetailsService(dummyUserDetailsService());
        provider.supports(KerberosServiceRequestToken.class);
        return provider;
    }


    @Bean
    public SunJaasKerberosTicketValidator sunJaasKerberosTicketValidator() {
        SunJaasKerberosTicketValidator ticketValidator = new SunJaasKerberosTicketValidator();
        //ticketValidator.setServicePrincipal(servicePrincipal);
        //ticketValidator.setKeyTabLocation(new UrlResource(keytabLocation));
        ticketValidator.setServicePrincipal("HTTP/example.net@EXAMPLE.COM");
       // ticketValidator.setServicePrincipal("ldap/example.net@EXAMPLE.COM");        
        FileSystemResource fs = new FileSystemResource("c:\\keytabs\\webapp.keytab"); 
        System.out.println("Initializing Kerberos KEYTAB file path:" + fs.getFilename() + "file exist: " + fs.exists());
        ticketValidator.setKeyTabLocation(fs);
        ticketValidator.setDebug(true);
        return ticketValidator;
    }

   /* @Bean
    public UserDetailsService kerbUserDetailsService() {
        return (username)->{
                return new User(username, "notUsed", true, true,
                        true, true, AuthorityUtils.createAuthorityList("ROLE_USER"));
        };
    }*/
    
    @Bean
	public KerberosLdapContextSource kerberosLdapContextSource() throws Exception {
		KerberosLdapContextSource contextSource = new KerberosLdapContextSource("ldap://example.net:10389");
		contextSource.setLoginConfig(loginConfig());
		
		return contextSource;
	}
    
    public SunJaasKrb5LoginConfig loginConfig() throws Exception {
		SunJaasKrb5LoginConfig loginConfig = new SunJaasKrb5LoginConfig();
		loginConfig.setKeyTabLocation(new FileSystemResource("c:\\keytabs\\webapp.keytab"));
		loginConfig.setServicePrincipal("HTTP/example.net@EXAMPLE.COM");
		//loginConfig.setServicePrincipal("ldap/example.net@EXAMPLE.COM");
		loginConfig.setDebug(true);
		loginConfig.setIsInitiator(true);
		loginConfig.afterPropertiesSet();
		return loginConfig;
	}
    
    @Bean
    public DummyUserDetailsService dummyUserDetailsService() {
      return new DummyUserDetailsService();
    }
    
    @Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
