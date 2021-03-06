package com.example.KerberosAuth;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DummyUserDetailsService implements UserDetailsService {

	 
	  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
	    System.out.println(s);
	    return new User(s, "notUsed", true, true, true, true, AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));
	  }
	}