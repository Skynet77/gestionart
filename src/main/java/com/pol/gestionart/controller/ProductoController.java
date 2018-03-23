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

import com.pol.gestionart.entity.Producto;
import com.pol.gestionart.service.ProductoServiceImpl;

@Controller
@RequestMapping("/producto")

public class ProductoController {
	
	private static final String PRODUCTO_VIEW = "/producto/producto";
	
	private static final Log LOG = LogFactory.getLog(ProductoController.class);
	
	@Autowired
	private ProductoServiceImpl productoService;
	
	@GetMapping("/listar")
	public ModelAndView listarProductos(){
		LOG.info("Call: "+ "listarProductos()");
		ModelAndView mav = new ModelAndView(PRODUCTO_VIEW);
		mav.addObject("producto", productoService.listAllProducts());
		return mav;
	}
	
	@PostMapping("/agregarProducto")
	public String agregarProducto(@ModelAttribute("producto") Producto producto) {
		LOG.info("Call: " + "agregarProducto()");
		productoService.saveProduct(producto);
		return "redirect:/producto/listar";
	}
	
	@RequestMapping("/eliminarProducto")
	public String eliminarProducto(@PathVariable String Id) {
		LOG.info("Call: " + "eliminarProducto()");
		productoService.deleteProduct(Id);
		return "redirect:/producto";
		
		
	}
	
	
	
	
	
	

}
