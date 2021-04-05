package com.example.KerberosAuth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.kerberos.authentication.sun.GlobalSunJaasKerberosConfig;

@Configuration
public class GlobalSecurityConfig {
	
	@Bean
    public GlobalSunJaasKerberosConfig globalSunJaasKerberosConfig() {
        GlobalSunJaasKerberosConfig globalConfig = new GlobalSunJaasKerberosConfig();
       globalConfig.setDebug(true);
        globalConfig.setKrbConfLocation("C:\\Program Files\\Java\\jdk1.8.0_281\\jre\\lib\\security\\krb5.conf");
        System.out.println("Global config ini file " + globalConfig.toString());
        return globalConfig;
    }

}
