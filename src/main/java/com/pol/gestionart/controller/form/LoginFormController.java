package com.pol.gestionart.controller.form;

import javax.validation.Valid;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pol.gestionart.entity.Producto;



@Controller
@Scope("request")
//@RequestMapping("login")
public class LoginFormController  {
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String accion2(ModelMap map, @Valid Producto obj,
			BindingResult bindingResult,
			@RequestParam(required = false) String accion,
			@RequestParam(value = "id_objeto", required = false) Long id_objeto) {
		
	}
	
}