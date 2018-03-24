package com.pol.gestionart.controller;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pol.gestionart.entity.Usuario;
import com.pol.gestionart.service.UsuarioServiceImpl;

@Controller
@RequestMapping("/usuario")

public class UsuarioController {
	
	private static final String USUARIO_VIEW = "/usuario/usuario";
	
	private static final Log LOG = LogFactory.getLog(UsuarioController.class);
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@GetMapping("/listar")
	public ModelAndView listAllUsers(){
		LOG.info("Call: "+ "listAllUsers()");
		ModelAndView mav = new ModelAndView(USUARIO_VIEW);
		mav.addObject("usuario", usuarioService.listAllUsers());
		return mav;
	}
	
	@PostMapping("/crearUsuario")
	public String addUser(@ModelAttribute("usuario") Usuario usuario) {
		LOG.info("Call: " + "addUser()" + " -- Param: " + usuario.toString());
		usuarioService.addUser(usuario);
		return "redirect:/usuario/listar";
	}
	
	@RequestMapping("/eliminarUsuario")
	public String deleteUser(@PathVariable String Id) {
		LOG.info("Call: " + "deleteUser()");
		usuarioService.deleteUser(Id);
		return "redirect:/usuario";
		
		
	}
	
	
	
	
	
	

}