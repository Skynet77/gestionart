package com.pol.gestionart.controller.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pol.gestionart.bean.LoginBean;
import com.pol.gestionart.dao.UsuarioDao;



@Controller
//@Scope("request")
//@RequestMapping("login")
public class LoginFormController {
	@Autowired
	private UsuarioDao usuariodao;
	
	  @RequestMapping(value = "/login", method = RequestMethod.GET)
	  public String init(Model model) {
	    model.addAttribute("msg", "Please Enter Your Login Details");
	    model.addAttribute("loginBean", new LoginBean());
	    return "login";
	  }
	  /*@RequestMapping(value = "/login", method = RequestMethod.POST)
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
	  }*/
	  
	  @RequestMapping(value={"/loginsucces","/"},method = RequestMethod.GET)
		public String loginCheck(){
			return null;
		}
	}