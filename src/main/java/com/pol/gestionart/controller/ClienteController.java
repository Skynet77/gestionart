package com.pol.gestionart.controller;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
//	@GetMapping("/listar")
//	public ModelAndView listarClientes() {
//		LOG.info("Call: " + "listarClientes()");
//		ModelAndView mav = new ModelAndView(CLIENTE_VIEW);
//		mav.addObject("clientes",clienteService.listarClientes());
//		return mav;
//	}
//	
	@PostMapping("/agregarCliente")
	public String agregarCliente(@ModelAttribute("cliente") Cliente cliente, Model model) {
		LOG.info("Call: " + "agregarCliente");
		clienteService.create(cliente);
		model.addAttribute("clientes"); //revisar porque habia dos arg
		return "redirect:/cliente/agregar"; //nombre del mapping	
	}
	
	@PostMapping("/actualizarCliente")
	public String modificarCliente(@ModelAttribute("cliente") Cliente cliente, Model model) {
		LOG.info("Call: " + "actualizarCliente()" + " -- Param " + cliente.toString()); //agrego metodo to string
		clienteService.createOrUpdate(cliente);
		model.addAttribute("clientes"); //revisar porque habia dos arg
		return "redirect:/cliente/actualizar"; //nombre del mapping	
	}
	
	@RequestMapping("/buscarCliente")
	public String buscarCliente(@PathVariable Long Id) {
		LOG.info("Call: " + "buscarCliente()");
		clienteService.find(Id);
		return "redirect:/cliente/buscar";
	}
	
	@RequestMapping("/eliminarCliente")
	public String eliminarCliente(@PathVariable String Id) {
		LOG.info("Call: " + "eliminarCliente()");
		clienteService.destroy(Id);
		return "redirect:/cliente/eliminar";
	}
}

