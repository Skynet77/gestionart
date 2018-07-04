package com.pol.gestionart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pol.gestionart.bean.Donut;
import com.pol.gestionart.dao.ClienteDao;
import com.pol.gestionart.dao.ProductoDao;
import com.pol.gestionart.dao.VentaDetalleDao;

@Controller
public class HomeController {
	
	private static final String HOME_VIEW = "index";
	
	private static final Log LOG = LogFactory.getLog(HomeController.class);
	
	@Autowired
	private VentaDetalleDao ventaDetalleDao;

	@Autowired
	private ClienteDao clienteDao;
	
	@Autowired
	private ProductoDao productoDao;
	
	
	@RequestMapping("/")
	public String listarClientes(Model map , HttpSession session) {
		LOG.info("Call: HomeController /");
		if(session.getAttribute("usuariologin") ==null) {
			return "redirect:/login";
		}
		//ModelAndView mav = new ModelAndView();
		map.addAttribute("usuariologin", session.getAttribute("usuariologin"));
		session.setAttribute("cantidadVentas", ventaDetalleDao.countVentaCabecera());
		session.setAttribute("cantidadClientes", clienteDao.getCantidadCliente());
		productoDao.getDonutReport();
		return HOME_VIEW;
	}
	
	@RequestMapping(value = "donut-data", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public List<Donut> jsonDunot(){
		List<Donut> list = productoDao.getDonutReport();
		return list;
	}

}
