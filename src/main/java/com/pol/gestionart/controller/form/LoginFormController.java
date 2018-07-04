package com.pol.gestionart.controller.form;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.pol.gestionart.bean.LoginBean;
import com.pol.gestionart.dao.UsuarioDao;
import com.pol.gestionart.entity.Usuario;



@Controller
//@Scope("request")
//@RequestMapping("login")
public class LoginFormController {
	@Autowired
	private UsuarioDao usuariodao;
	
	  @RequestMapping(value = "/login", method = RequestMethod.GET)
	  public String init(Model model) {
	    
	    model.addAttribute("loginBean", new LoginBean());
	    return "login";
	  }
	  @RequestMapping(value = "/login", method = RequestMethod.POST)
	  public String submit(Model model, HttpSession sesion, @ModelAttribute("loginBean") LoginBean loginBean) {
	    if (loginBean != null && loginBean.getCedula() != null & loginBean.getPassword() != null) {
	      Usuario usuario = usuariodao.getUsuarioByUserName(loginBean.getCedula());
	      if ( usuario != null && loginBean.getPassword().equals(usuario.getPassword())) {
	        model.addAttribute("msg", loginBean.getCedula());
	        sesion.setAttribute("usuariologin",  usuario);
	        return "redirect:/";
	      } else {
	        model.addAttribute("errorMsg", "Datos Incorrectos");
	        model.addAttribute("loginBean", new LoginBean());
	        return "/login";
	      }
	    } else {
	      model.addAttribute("error", "Please enter Details");
	      return "login";
	    }
	  }
	  
	  @RequestMapping(value = "/logout", method = RequestMethod.GET)
	  public String logout(Model model, HttpSession session) {
		  session.setAttribute("usuariologin",  null);
	    model.addAttribute("loginBean", new LoginBean());
	    return "redirect:/login";
	  }
	}