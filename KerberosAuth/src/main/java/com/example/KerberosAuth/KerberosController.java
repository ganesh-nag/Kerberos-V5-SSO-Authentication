package com.example.KerberosAuth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class KerberosController {
	
	@GetMapping(value="/")
    public ModelAndView home(ModelAndView modelAndView) {
        modelAndView.setViewName("home");
        return modelAndView;
    }

	@RequestMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }
	
	@GetMapping(value="/protected")
    public ModelAndView resource(ModelAndView modelAndView) {
        modelAndView.setViewName("resource");
        return modelAndView;
    }
}
