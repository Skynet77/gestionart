package com.pol.gestionart.controller;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pol.gestionart.entity.Cliente;

@Controller
public class HomeController {
	
	private static final String HOME_VIEW = "starter";
	
	private static final Log LOG = LogFactory.getLog(HomeController.class);
	
	@RequestMapping("/")
	public ModelAndView listarClientes() {
		LOG.info("Call: HomeController /");
		ModelAndView mav = new ModelAndView(HOME_VIEW);
		return mav;
	}


}
