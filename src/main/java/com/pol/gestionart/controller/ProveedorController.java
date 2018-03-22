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

import com.pol.gestionart.entity.Proveedor;
import com.pol.gestionart.service.ProveedorServiceImpl;

@Controller
@RequestMapping("/proveedor")

public class ProveedorController {
	
	private static final String PROVEEDOR_VIEW = "/proveedor/proveedor";
	
	private static final Log LOG = LogFactory.getLog(ProveedorController.class);
	
	@Autowired
	private ProveedorServiceImpl proveedorService;
	
	@GetMapping("/listar")
	public ModelAndView listarProveedor(){
		LOG.info("Call: "+ "listarProveedor()");
		ModelAndView mav = new ModelAndView(PROVEEDOR_VIEW);
		mav.addObject("proveedor", proveedorService.listAllProv());
		return mav;
	}
	
	@PostMapping("/agregarProveedor")
	public String agregarProveedor(@ModelAttribute("proveedor") Proveedor proveedor) {
		LOG.info("Call: " + "agregarProveedor()");
		proveedorService.saveProv(proveedor);
		return "redirect:/proveedor/listar";
	}
	
	@RequestMapping("/eliminarProveedor")
	public String eliminarProveedor(@PathVariable String Id) {
		LOG.info("Call: " + "eliminarProveedor()");
		proveedorService.deleteProv(Id);
		return "redirect:/proveedor";
		
		
	}
}