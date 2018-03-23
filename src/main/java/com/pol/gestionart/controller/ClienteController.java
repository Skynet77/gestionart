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

import com.pol.gestionart.entity.Cliente;
import com.pol.gestionart.service.ClienteServiceImpl;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
	
	private static final String CLIENTE_VIEW = "/cliente/cliente";
	
	private static final Log LOG = LogFactory.getLog(ClienteController.class);
	
	@Autowired
	private ClienteServiceImpl clienteService;
	
	@GetMapping("/listar")
	public ModelAndView listarClientes() {
		LOG.info("Call: " + "listarClientes()");
		ModelAndView mav = new ModelAndView(CLIENTE_VIEW);
		mav.addObject("clientes",clienteService.listarClientes());
		return mav;
	}
	
	@PostMapping("/agregarCliente")
	public String agregarCliente(@ModelAttribute("cliente") Cliente cliente) {
		LOG.info("Call: " + "agregarCliente()");
		clienteService.agregarCliente(cliente);
		return "redirect:/cliente/listar"; //nombre del mapping
		
	}
	
	@RequestMapping("/eliminarCliente")
	public String eliminarCliente(@PathVariable String Id) {
		LOG.info("Call: " + "eliminarCliente()");
		clienteService.eliminarCliente(Id);
		return "redirect:/products";
		
	}

}
