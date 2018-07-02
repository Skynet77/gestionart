package com.pol.gestionart.controller.form;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pol.gestionart.bean.InventarioDetalle;
import com.pol.gestionart.controller.list.InventarioListController;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.dao.InventarioDao;
import com.pol.gestionart.dao.InventarioDetalleDao;
import com.pol.gestionart.dao.ProductoDao;
import com.pol.gestionart.entity.Inventario;
import com.pol.gestionart.entity.InventarioDetalleTable;
import com.pol.gestionart.entity.Producto;
import com.pol.gestionart.exceptions.AjaxException;
import com.pol.gestionart.util.GeneralUtils;

@Controller
@Scope("request")
@RequestMapping("inventario")
public class InventarioFormController extends FormController<Inventario> {

	@Autowired
	private InventarioDao inventarioDao;
	
	@Autowired
	private ProductoDao productoDao;
	
	@Autowired
	private InventarioDetalleDao inventarioDetalleDao;

	@Autowired
	private InventarioListController inventarioList;

	@Override
	public String getTemplatePath() {
		return "inventario/inventario_index";
	}

	@Override
	public String getNombreObjeto() {
		return "inventario";
	}

	@Override
	public Inventario getNuevaInstancia() {
		return new Inventario();
	}

	@Override
	public Dao<Inventario> getDao() {
		return inventarioDao;
	}

	@Override
	public void agregarValoresAdicionales(ModelMap map) {
		map.addAttribute("columnas", inventarioList.getColumnas());
		map.addAttribute("columnasStr", inventarioList.getColumnasStr(null));
		super.agregarValoresAdicionales(map);
	}

	@RequestMapping(value = "inventario_detalle", method = RequestMethod.POST)
	public String inventarioDetalle(ModelMap map, HttpSession session, @RequestParam(value = "id_prod") Long idProd,
			@RequestParam(value = "mes") int mesFe) {
		agregarValoresAdicionales(map);
		List<InventarioDetalle> inventarioDetalle = inventarioDao.joinInventario(idProd, mesFe);
		map.addAttribute("listDetalle", inventarioDetalle);
		return "inventario/modal_inventario";
	}

	@RequestMapping(value = "ajuste_inventario", method = RequestMethod.GET)
	public String ajusteInventario(ModelMap map, HttpSession session) {
		agregarValoresAdicionales(map);

		if(session.getAttribute("msgAjuste")!=null){
			map.addAttribute("msgExito", session.getAttribute("msgAjuste"));
			session.setAttribute("msgAjuste",null);
		}
		return "inventario/ajuste_inventario";
	}

	@RequestMapping(value = "registrar", method = RequestMethod.POST)
	public @ResponseBody InventarioDetalleTable ajusteInventario(ModelMap map, HttpSession session, @RequestParam(value = "id_prod") Long idProd,
			@RequestParam(value = "fecha_mes") String fechaMes, @RequestParam(value = "cantidad") int cantidad) throws AjaxException {
		InventarioDetalleTable inv = new InventarioDetalleTable();
		try {
		Producto produc = productoDao.find(idProd);
		if(cantidad >0){
			int nuevaCantidad = produc.getCantidad()+cantidad;
			produc.setCantidad(nuevaCantidad);
			productoDao.createOrUpdate(produc);
		}else if(cantidad < 0){
			int nuevaCantidad = produc.getCantidad()+cantidad;
			produc.setCantidad(nuevaCantidad);
			productoDao.createOrUpdate(produc);
		}
		
		inv.setCantidad(cantidad);
		inv.setFecha(GeneralUtils.getStringFromDate(new Date(), GeneralUtils.DATE_FORMAT_GUION));
		inv.setIdProducto(idProd);
		inv.setMes(Integer.parseInt(fechaMes.substring(0, 2)));
		inv.setComprobante("00000");
		inv.setOperacion("AJUSTE INVENTARIO");
		inv.setProveedorCliente("SIN NOMBRE");
		inv.setFechaMes(new Date());
		inventarioDetalleDao.create(inv);
		
		Inventario inventarioMesAnterio = null;
		inventarioMesAnterio = inventarioDao.getInventarioByProductoFecha(idProd,Integer.parseInt(fechaMes.substring(0, 2)));
		if(inventarioMesAnterio != null){
			inventarioMesAnterio.setActual(inventarioMesAnterio.getActual()+cantidad);
			if(cantidad >0){
			inventarioMesAnterio.setEntrada(inventarioMesAnterio.getEntrada()+cantidad);
			}else if(cantidad < 0){
				//+ por que es negativo
				inventarioMesAnterio.setSalida(inventarioMesAnterio.getSalida()+cantidad);	
			}
		}	
		inventarioDao.createOrUpdate(inventarioMesAnterio);
		session.setAttribute("msgAjuste", "Ajuste realizado con éxito!");
		} catch (Exception e) {
			throw new AjaxException("Ocurrió un error al intentar realizar el ajuste");
		}
		return inv;
	}

}