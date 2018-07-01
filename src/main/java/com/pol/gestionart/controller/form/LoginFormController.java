package com.pol.gestionart.controller.form;

import javax.validation.Valid;

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



@Controller
//@Scope("request")
//@RequestMapping("login")
public class LoginFormController {
	  @RequestMapping(value = "/login", method = RequestMethod.GET)
	  public String init(Model model) {
	    model.addAttribute("msg", "Please Enter Your Login Details");
	    return "login";
	  }
	  @RequestMapping(value = "/login", method = RequestMethod.POST)
	  public String submit(Model model, @ModelAttribute("loginBean") LoginBean loginBean) {
	    if (loginBean != null && loginBean.getUserName() != null & loginBean.getPassword() != null) {
	      if (loginBean.getUserName().equals("chandra") && loginBean.getPassword().equals("chandra123")) {
	        model.addAttribute("msg", loginBean.getUserName());
	        return "success";
	      } else {
	        model.addAttribute("error", "Invalid Details");
	        return "login";
	      }
	    } else {
	      model.addAttribute("error", "Please enter Details");
	      return "login";
	    }
	  }
	}