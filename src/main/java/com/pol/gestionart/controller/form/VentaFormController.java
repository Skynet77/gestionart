package com.pol.gestionart.controller.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pol.gestionart.controller.list.VentaCabeceraListController;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.dao.ProductoDao;
import com.pol.gestionart.dao.VentaCabeceraDao;
import com.pol.gestionart.entity.Producto;
import com.pol.gestionart.entity.VentaCabecera;
import com.pol.gestionart.entity.VentaDetalle;



@Controller
@Scope("request")
@RequestMapping("venta")
public class VentaFormController extends FormController<VentaCabecera> {

	public static final String LISTA_DETALLE = "listaDetalle";
	public static final String LISTA_PRODUCTO = "listaProducto";
	public static final String VENTA_CABECERA = "ventaCabecera";
	public static final String VENTA_DETALLE = "ventaDetalle";
	public static final String PRODUCTO_DETALLE = "productoDetalle";
	@Autowired
	private VentaCabeceraDao ventaCabeceraDao;
	

	@Autowired
	private VentaCabeceraListController productoList;
	
	@Autowired 
	private ProductoDao productoDao;

	
	@Override
	public String getTemplatePath() {
		return "venta/venta_index";
	}

	@Override
	public String getNombreObjeto() {
		return "venta";
	}

	@Override
	public VentaCabecera getNuevaInstancia() {
		return new VentaCabecera();
	}

	@Override
	public void agregarValoresAdicionales(ModelMap map) {
		map.addAttribute("columnas", productoList.getColumnas());
		map.addAttribute("columnasStr", productoList.getColumnasStr(null));
		map.addAttribute("productoList", getDao().getList(0, 100, null));
		map.addAttribute("tituloFormulario", "Registrar Venta");
		map.addAttribute("producto", new VentaCabecera());
		map.addAttribute("accion", "guardar");
		map.addAttribute("ventaList", getDao().findEntities(true,-1,-1, "VentaCabecera"));
		map.addAttribute("titlePage", "Registro de Venta");
		super.agregarValoresAdicionales(map);
	}
	
	
	
	@RequestMapping(value = "agregar-detalle", method = RequestMethod.POST)
	public String addProducto(ModelMap map,
			@RequestParam(required = true, value="id_producto") Long idProducto, 
			@RequestParam(required = true, value="cantidad") BigDecimal cantidad, HttpSession session) {
		
		List<Producto> listProducto = null;
		VentaCabecera ventaCab = null;
		VentaDetalle ventaDet = null;
		List<VentaDetalle> listDetalle = null;
		BigDecimal montoTotal;
		if(session.getAttribute(LISTA_PRODUCTO)!=null){
			listProducto = (List<Producto>) session.getAttribute(LISTA_PRODUCTO);
		}else{
			listProducto = new ArrayList<>();
		}
		
		if(session.getAttribute(LISTA_DETALLE)!=null){
			listDetalle = (List<VentaDetalle>) session.getAttribute(LISTA_DETALLE);
		}else{
			listDetalle = new ArrayList<>();
		}
		
		if(session.getAttribute(VENTA_CABECERA)!=null){
			ventaCab = (VentaCabecera) session.getAttribute(VENTA_CABECERA);
		}else{
			ventaCab = new VentaCabecera();
		}
		
		Producto producto = productoDao.find(idProducto);
		if(producto != null){
			listProducto.add(producto);
		}
		//obtenemos el monto total de la cabecera
		if(ventaCab.getMontoTotal()!=null){
			montoTotal = ventaCab.getMontoTotal();
		}else{
			montoTotal = new BigDecimal(0);
		}
		
		//multiplicamos el precio de venta por la cantidad
		BigDecimal montoVenta = producto.getPrecioVenta().multiply(cantidad);
		//sumamos el monto total que teniamos por 
		montoTotal = montoTotal.add(montoVenta);
		//volvemos a guardar el monto total
		ventaCab.setMontoTotal(montoTotal);
		//el detalle actual que se esta procesando
		ventaDet = new VentaDetalle();
		ventaDet.setCantidad(cantidad);
		ventaDet.setPrecioTotal(montoVenta);
		ventaDet.setPrecioUnitario(producto.getPrecioVenta());
		ventaDet.setProducto(producto);
		ventaDet.setVentaCabecera(ventaCab);
		listDetalle.add(ventaDet);
		session.setAttribute(LISTA_DETALLE,listDetalle);
		session.setAttribute(VENTA_CABECERA, ventaCab);
		session.setAttribute(VENTA_DETALLE, listDetalle);
		map.addAttribute(LISTA_DETALLE,listDetalle);
		map.addAttribute(VENTA_CABECERA, ventaCab);
//		map.addAttribute(VENTA_DETALLE, ventaDet);
		map.addAttribute(VENTA_DETALLE, ventaDet);
		map.addAttribute(PRODUCTO_DETALLE, producto);
		
		
		return "venta/venta_detail";

	}
	
	

	@RequestMapping(value = "accion2", method = RequestMethod.POST)
	public String accion2(ModelMap map, @Valid VentaCabecera obj,
			BindingResult bindingResult,
			@RequestParam(required = false) String accion,
			@RequestParam(value = "id_objeto", required = false) Long id_objeto) {
		if (StringUtils.equals(accion, "save")) {
			return guardar(map, obj, bindingResult);
		} else if (StringUtils.equals(accion, "edit")) {
			logger.info("OBJETO PROCESO {}", obj);
			return edit(map, obj.getId(),obj);
		} else if ("delete".equals(accion)) {
			//return editarEstado(map, id_objeto);

		}
		return getTemplatePath();

	}
	
	
	
	
	
	@Override
	public Dao<VentaCabecera> getDao() {
		return ventaCabeceraDao;
	}

}