package com.example.KerberosAuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KerberosAuthApplication {
	
	static {
		
		//System.setProperty("java.security.krb5.conf","C:\\Program Files\\Java\\jdk1.8.0_281\\jre\\lib\\security\\krb5.conf");
		System.out.println("krb5.conf set!!!!! " + System.getProperty("java.security.krb5.conf"));
		//System.setProperty("java.security.auth.login.config","C:\\Program Files\\Java\\jdk1.8.0_281\\jre\\lib\\security\\spnegoLogin.conf");
		System.out.println("spnegoLogin conf set!!!! " + System.getProperty("java.security.auth.login.config"));
		//System.out.println("test value!!! " + System.getProperty("test"));
	}

	public static void main(String[] args) {
		SpringApplication.run(KerberosAuthApplication.class, args);
	}

}
