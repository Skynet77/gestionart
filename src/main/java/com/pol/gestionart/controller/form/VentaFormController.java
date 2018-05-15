package com.pol.gestionart.controller.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pol.gestionart.controller.list.VentaCabeceraListController;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.dao.ProductoDao;
import com.pol.gestionart.dao.VentaCabeceraDao;
import com.pol.gestionart.dao.VentaDetalleDao;
import com.pol.gestionart.entity.Producto;
import com.pol.gestionart.entity.VentaCabecera;
import com.pol.gestionart.entity.VentaCabecera.Estado;
import com.pol.gestionart.entity.VentaDetalle;
import com.pol.gestionart.util.GeneralUtils;



@Controller
@Scope("request")
@RequestMapping("venta")
public class VentaFormController extends FormController<VentaCabecera> {

	public static final String LISTA_DETALLE = "listaDetalle";
	public static final String MAP_DETALLE = "mapDetalle";
	public static final String LISTA_PRODUCTO = "listaProducto";
	public static final String VENTA_CABECERA = "ventaCabecera";
	public static final String VENTA_DETALLE = "ventaDetalle";
	public static final String PRODUCTO_DETALLE = "productoDetalle";
	public static final BigDecimal IVA_10 = new BigDecimal(1.1);

	@Autowired
	private VentaCabeceraListController productoList;
	
	@Autowired 
	private ProductoDao productoDao;
	
	@Autowired
	private VentaCabeceraDao ventaCabeceraDao;
	
	@Autowired
	private VentaDetalleDao ventaDetalleDao;
	
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
		map.addAttribute("ventaCabecera",new VentaCabecera());
		map.addAttribute("ventaDetalle",new VentaDetalle());
		
		
		super.agregarValoresAdicionales(map);
	}
	
	@RequestMapping("pay")
	public String index(ModelMap map,HttpSession session) {
		
		map.addAttribute(getNombreObjeto(), getNuevaInstancia());
		agregarValoresAdicionales(map);
		session.setAttribute(MAP_DETALLE,null);
		session.setAttribute(VENTA_CABECERA, null);
		session.setAttribute(VENTA_DETALLE, null);
		return "venta/venta_pay"; 
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
		
		
		//calculo de subTotal
		BigDecimal subTotal;
		BigDecimal IVA;
		if(ventaCab.getSubTotal()!=null){
			subTotal = ventaCab.getSubTotal();
		}else{
			subTotal = new BigDecimal(0);
		}
		subTotal = ventaCab.getMontoTotal().divide(IVA_10,2,BigDecimal.ROUND_HALF_UP);
		ventaCab.setSubTotal(subTotal);
		IVA = subTotal.multiply(new BigDecimal(0.1));
		ventaCab.setIva(IVA);
		
		//el detalle actual que se esta procesando
		ventaDet = new VentaDetalle();
		ventaDet.setCantidad(cantidad);
		ventaDet.setPrecioTotal(montoVenta);
		ventaDet.setPrecioUnitario(producto.getPrecioVenta());
		ventaDet.setProducto(producto);
		ventaDet.setVentaCabecera(ventaCab);
		//listDetalle.add(ventaDet);
		Map<String, VentaDetalle> mapaVentaDetalle = GeneralUtils.mapSerializeVentaDetalleOrUpdate(session,ventaDet);
		session.setAttribute(MAP_DETALLE,mapaVentaDetalle);
//		session.setAttribute(LISTA_DETALLE,listDetalle);
		session.setAttribute(VENTA_CABECERA, ventaCab);
		session.setAttribute(VENTA_DETALLE, ventaDet);
		map.addAttribute(MAP_DETALLE,mapaVentaDetalle);
		map.addAttribute(VENTA_CABECERA, ventaCab);
		map.addAttribute(VENTA_DETALLE, ventaDet);
		map.addAttribute(PRODUCTO_DETALLE, producto);
		
		
		return "venta/venta_detail";

	}
	
	@RequestMapping(value = "eliminar-detalle", method = RequestMethod.POST)
	public String deleteProducto(ModelMap map,
			@RequestParam(required = true, value="id_producto") String uuid,  HttpSession session) {
		Map<String, VentaDetalle> mapVentaDetail = new HashMap<>();
		VentaCabecera ventaCab = null;
		VentaDetalle ventaDet = null;
		
		if(session.getAttribute(VENTA_CABECERA)!=null){
			ventaCab = (VentaCabecera) session.getAttribute(VENTA_CABECERA);
		}else{
			ventaCab = new VentaCabecera();
		}
		
		if(uuid!=null){
			mapVentaDetail = (Map<String, VentaDetalle>) session.getAttribute(MAP_DETALLE);
			ventaDet = mapVentaDetail.get(uuid);
			ventaCab.setMontoTotal(ventaCab.getMontoTotal().subtract(ventaDet.getPrecioTotal()));
			
			//calculo de subTotal
			BigDecimal subTotal;
			BigDecimal IVA;
			subTotal = ventaCab.getMontoTotal().divide(IVA_10,2,BigDecimal.ROUND_HALF_UP);
			ventaCab.setSubTotal(subTotal);
			IVA = subTotal.multiply(new BigDecimal(0.1));
			ventaCab.setIva(IVA);
			
			mapVentaDetail.remove(uuid);
			session.setAttribute(MAP_DETALLE,mapVentaDetail);
			map.addAttribute(MAP_DETALLE,mapVentaDetail);
		}
		session.setAttribute(VENTA_CABECERA, ventaCab);
		map.addAttribute(VENTA_CABECERA, ventaCab);
		
		return "venta/venta_detail";
	}
	
	

	@RequestMapping(value = "confirmar", method = RequestMethod.POST)
	public String confirmarVenta(ModelMap map, @Valid VentaCabecera ventaCabecera,HttpSession session) {
		
		VentaCabecera ventaCab = null;
		VentaDetalle ventaDet = null;
		Map<String, VentaDetalle> mapaVentaDetalle = null;
		
		if(session.getAttribute(VENTA_CABECERA)!=null){
			ventaCab = (VentaCabecera) session.getAttribute(VENTA_CABECERA);
		}else{
			ventaCab = new VentaCabecera();
		}
		
		if(session.getAttribute(MAP_DETALLE)!=null){
			mapaVentaDetalle  = (Map<String, VentaDetalle>) session.getAttribute(MAP_DETALLE);
		}
		
		ventaCabecera.setIva(ventaCab.getIva());
		ventaCabecera.setNroComprobante("1");
		ventaCabecera.setEstado(Estado.CONFIRMADO.name());
		ventaCabeceraDao.create(ventaCabecera);
		
		for (VentaDetalle vd : mapaVentaDetalle.values()) {
			vd.setVentaCabecera(ventaCabecera);
			ventaDetalleDao.create(vd);
		}
		return "";

	}
	
	
	
	
	
	@Override
	public Dao<VentaCabecera> getDao() {
		return ventaCabeceraDao;
	}

}