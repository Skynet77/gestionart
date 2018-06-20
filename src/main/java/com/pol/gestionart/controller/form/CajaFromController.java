package com.pol.gestionart.controller.form;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pol.gestionart.controller.list.CajaListController;
import com.pol.gestionart.controller.list.VentaCabeceraListController;
import com.pol.gestionart.dao.CajaDao;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.dao.InventarioDao;
import com.pol.gestionart.dao.ProductoDao;
import com.pol.gestionart.dao.VentaCabeceraDao;
import com.pol.gestionart.dao.VentaDetalleDao;
import com.pol.gestionart.entity.Caja;
import com.pol.gestionart.entity.Caja.DescripcionCaja;
import com.pol.gestionart.entity.Inventario;
import com.pol.gestionart.entity.Producto;
import com.pol.gestionart.entity.VentaCabecera;
import com.pol.gestionart.entity.VentaCabecera.Estado;
import com.pol.gestionart.entity.VentaDetalle;
import com.pol.gestionart.exceptions.WebAppException;
import com.pol.gestionart.util.GeneralUtils;



@Controller
@Scope("request")
@RequestMapping("caja")
public class CajaFromController extends FormController<Caja> {

	@Autowired
	private CajaDao cajaDao;
	
	@Autowired
	private VentaCabeceraDao ventaCabeceraDao;
	
	@Autowired
	private CajaListController cajaList;
	
	@Autowired
	private VentaCabeceraListController productoList;

	@Autowired
	private VentaDetalleDao ventaDetalleDao;
	
	@Autowired
	private ProductoDao productoDao;
	
	@Autowired
	private InventarioDao inventarioDao;
	
	@Override
	public String getTemplatePath() {
		return "caja/caja_index";
	}

	@Override
	public String getNombreObjeto() {
		return "caja";
	}

	@Override
	public Caja getNuevaInstancia() {
		return new Caja();
	}

	@Override
	public Dao<Caja> getDao() {
		return cajaDao;
	}
	
	@Override
	public void agregarValoresAdicionales(ModelMap map) {
		map.addAttribute("columnasVenta", productoList.getColumnas());
		map.addAttribute("columnas", cajaList.getColumnas());
		map.addAttribute("columnasStr", cajaList.getColumnasStr(null));
		map.addAttribute("cajaList", getDao().getList(0, 100, null));
		map.addAttribute("caja", new Caja());
		map.addAttribute("tituloFormulario", "Registrar Caja");
		map.addAttribute("accion", "guardar");
		List<DescripcionCaja> listTipoCaja = Arrays.asList(DescripcionCaja.values());
		map.addAttribute("acciones", listTipoCaja);		
		super.agregarValoresAdicionales(map);
	}
	
	@RequestMapping("registro")
	public String ingreso(ModelMap map,HttpSession session, Caja caja) {
		Calendar calendar = Calendar.getInstance();
		//calendar.set(Calendar.DATE, 1);
		Date d = new Date(calendar.getTimeInMillis());
		String fechaActual = GeneralUtils.getStringFromDate(d,GeneralUtils.DATE_FORMAT_CAJA);
		Caja addCaja = new Caja();
		addCaja.setFechaActual(d);
		Caja cajaActual = cajaDao.findCajaByDate();
		BigDecimal saldoActual = BigDecimal.ZERO; 
		
		if(!(caja.getEntrada()==null)){
			addCaja.setDescripcion(caja.getDescripcion());
			addCaja.setEntradaBigDecimal(caja.getEntrada());
			addCaja.setFecha(fechaActual);
			
			if(cajaActual!=null){
				saldoActual  = cajaActual.getSaldoActual().add(caja.getEntrada());
			}else{
				saldoActual = caja.getEntrada();
			}
			
			addCaja.setSaldoActual(saldoActual);
			addCaja.setSalidaBigDecimal(BigDecimal.ZERO);
			map.addAttribute("msgExito", "Ingreso registrado correctamente");
		}else if(!(caja.getSalida()==null)){
			addCaja.setDescripcion(caja.getDescripcion());
			addCaja.setSalidaBigDecimal(caja.getSalida());
			addCaja.setFecha(fechaActual);
			
			if(cajaActual!=null){
				saldoActual  = cajaActual.getSaldoActual().subtract(caja.getSalida());
			}else{
				saldoActual  = caja.getSalida();
			}
			
			addCaja.setSaldoActual(saldoActual);
			addCaja.setEntradaBigDecimal(BigDecimal.ZERO);
			map.addAttribute("msgExito", "Egreso registrado correctamente");
		}
		agregarValoresAdicionales(map);
		
		cajaDao.create(addCaja);
		return getTemplatePath(); 
	}
	
	@RequestMapping("confirmar")
	public String confirmarVenta(ModelMap map,HttpSession session, 
			@RequestParam(name="id_cabecera")Long idVentaCab,
			@RequestParam(name="conf")String accion) throws WebAppException {
		
		Caja cajaActual = cajaDao.findCajaByDate();
		if(cajaActual ==  null){
			throw new WebAppException("Debe abrir una caja antes de realizar una VENTA");
		}
		
		VentaCabecera ventaCabecera = ventaCabeceraDao.find(idVentaCab);
		Inventario inventario = null;
		if("confirmar".equals(accion)){
			if(ventaCabecera != null){
				ventaCabecera.setEstado(Estado.CONFIRMADO.name());
				
				Caja caja = new Caja();
				caja.setFecha(ventaCabecera.getFechaEmision());
				caja.setDescripcion("VENTA PRODUCTO");
				caja.setEntradaBigDecimal(ventaCabecera.getMontoTotal());
				if(cajaActual== null){
					caja.setSaldoActual(caja.getEntrada());
				}else{
					//si ya habia una caja abiert este dia entonces el saldoactual 
					//es la suma deloque ya existe mas la entrada
					caja.setSaldoActual(cajaActual.getSaldoActual().add(caja.getEntrada()));
				}
				caja.setFechaActual(new Date());
				caja.setFecha(ventaCabecera.getFechaEmision());
				caja.setSalidaBigDecimal(BigDecimal.ZERO);
				cajaDao.create(caja);
				
			}
		}else if("anular".equals(accion)){
			
			List<VentaDetalle>listDetalle = ventaCabeceraDao.getDetalleByIdCab(idVentaCab);
			for (VentaDetalle vd : listDetalle) {
				//preguntamos si ya hay un registro de inventario de ese producto en este mes
				inventario = inventarioDao.getInventarioByProductoFecha(vd.getProducto().getId(),null);
				inventario.setActual(inventario.getActual()+vd.getCantidad());
				inventario.setSalida(inventario.getSalida()-vd.getCantidad());
				inventarioDao.createOrUpdate(inventario);
				aumentarStock(vd.getProducto(), vd, map);
			}
			for (VentaDetalle ventaDetalle : listDetalle) {
				ventaDetalleDao.destroy(ventaDetalle);
			}
			ventaCabeceraDao.destroy(ventaCabecera);
		}
		
		
		
		return "redirect:/venta/a-confirmar";
	}
	
	private void aumentarStock(Producto producto, VentaDetalle ventaDet, ModelMap map){
		//AUMENTAMOS EL STOCK
		
		Producto productoAumentar = null;
		int sumar = 0;
		//aumentamos la cantidad del producto en stock
		productoAumentar = producto;
		sumar = productoAumentar.getCantidad() + ventaDet.getCantidad();
		productoAumentar.setCantidad(sumar);
		productoDao.createOrUpdate(productoAumentar);
	}
	
	
	@RequestMapping("caja-venta")
	public String getCajaVenta(ModelMap map,HttpSession session,
				@RequestParam(name="id_venta")Long idVentaCab) {
	//	ventaCabDetDao.find(id)
		
		
		return "";
		
	}
}