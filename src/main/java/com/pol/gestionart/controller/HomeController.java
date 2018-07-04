package com.pol.gestionart.controller;

import javax.servlet.http.HttpSession;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	private static final String HOME_VIEW = "index";
	
	private static final Log LOG = LogFactory.getLog(HomeController.class);
	
	@RequestMapping("/")
	public String listarClientes(Model map , HttpSession session) {
		LOG.info("Call: HomeController /");
		if(session.getAttribute("usuariologin") ==null) {
			return "redirect:/login";
		}
		//ModelAndView mav = new ModelAndView();
		map.addAttribute("usuariologin", session.getAttribute("usuariologin"));
		return HOME_VIEW;
	}


}
