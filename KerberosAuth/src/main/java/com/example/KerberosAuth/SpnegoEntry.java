package com.example.KerberosAuth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.kerberos.web.authentication.SpnegoEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class SpnegoEntry extends SpnegoEntryPoint{
	
	@Override 
	   public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
	       throws IOException, ServletException {  
		response.addHeader("WWW-Authenticate", "Negotiate");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	     PrintWriter writer = response.getWriter();  
	     writer.println("HTTP Status 401 - " + authEx.getMessage()); 
	     RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
	     System.out.println("in request dispatcher!!!!!!!!!");
		 dispatcher.forward(request, response);
	   } 
	   

}
