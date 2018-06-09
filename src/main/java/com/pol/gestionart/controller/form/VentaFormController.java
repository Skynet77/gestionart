package com.pol.gestionart.controller.form;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.pol.gestionart.dao.CajaDao;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.dao.ProductoDao;
import com.pol.gestionart.dao.VentaCabeceraDao;
import com.pol.gestionart.dao.VentaDetalleDao;
import com.pol.gestionart.entity.Caja;
import com.pol.gestionart.entity.Producto;
import com.pol.gestionart.entity.VentaCabecera;
import com.pol.gestionart.entity.VentaCabecera.Estado;
import com.pol.gestionart.entity.VentaDetalle;
import com.pol.gestionart.exceptions.AjaxException;
import com.pol.gestionart.exceptions.WebAppException;
import com.pol.gestionart.util.GeneralUtils;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;



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
	private static final String DATE_FORMAT = "dd/MM/yyyy-HH:mm:ss";

	@Autowired
	private VentaCabeceraListController productoList;
	
	@Autowired 
	private ProductoDao productoDao;
	
	@Autowired 
	private CajaDao cajaDao;
	
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
	public String indexPay(ModelMap map,HttpSession session) {
		
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
			@RequestParam(required = true, value="cantidad") int cantidad, HttpSession session) throws AjaxException {
		
		List<Producto> listProducto = null;
		VentaCabecera ventaCab = null;
		VentaDetalle ventaDet = null;
		List<VentaDetalle> listDetalle = null;
		Producto producto = null;
		BigDecimal montoTotal;
		try {
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
			
			producto = productoDao.find(idProducto);
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
			BigDecimal montoVenta = producto.getPrecioVentaBigDecimal().multiply(new BigDecimal(cantidad));
			//sumamos el monto total que teniamos por 
			montoTotal = montoTotal.add(montoVenta);
			//volvemos a guardar el monto total
			ventaCab.setMontoTotalBigDecimal(montoTotal);
			
			
			//calculo de subTotal
			BigDecimal subTotal;
			BigDecimal IVA;
			if(ventaCab.getSubTotal()!=null){
				subTotal = ventaCab.getSubTotal();
			}else{
				subTotal = new BigDecimal(0);
			}
			subTotal = ventaCab.getMontoTotal().divide(IVA_10,2,BigDecimal.ROUND_HALF_UP);
			ventaCab.setSubTotalBigDecimal(subTotal);
			IVA = subTotal.multiply(new BigDecimal(0.1));
			ventaCab.setIva(IVA);
			
			//el detalle actual que se esta procesando
			ventaDet = new VentaDetalle();
			ventaDet.setCantidad(cantidad);
			ventaDet.setPrecioTotal(montoVenta);
			ventaDet.setPrecioUnitario(producto.getPrecioVentaBigDecimal());
			ventaDet.setProducto(producto);
			ventaDet.setVentaCabecera(ventaCab);
		} catch (Exception e ) {
			throw new AjaxException("Ocurrió un error inesperado");
		}
		
		Map<String, VentaDetalle> mapaVentaDetalle = GeneralUtils.mapSerializeVentaDetalleOrUpdate(session,ventaDet);
		session.setAttribute(MAP_DETALLE,mapaVentaDetalle);
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
		int suma = 0;
		if(session.getAttribute(VENTA_CABECERA)!=null){
			ventaCab = (VentaCabecera) session.getAttribute(VENTA_CABECERA);
		}else{
			ventaCab = new VentaCabecera();
		}
		
		if(uuid!=null){
			mapVentaDetail = (Map<String, VentaDetalle>) session.getAttribute(MAP_DETALLE);
			ventaDet = mapVentaDetail.get(uuid);
			ventaCab.setMontoTotalBigDecimal(ventaCab.getMontoTotal().subtract(ventaDet.getPrecioTotal()));
			
			//producto para disminuir el stok
			Producto productoDisminuir = null;
			//sumamos la cantidad del producto en stock, ya que se elimino del detalle
			productoDisminuir = ventaDet.getProducto();
			suma = productoDisminuir.getCantidad() + ventaDet.getCantidad();
			productoDisminuir.setCantidad(suma);
			productoDao.createOrUpdate(productoDisminuir);
			
			//calculo de subTotal
			BigDecimal subTotal;
			BigDecimal IVA;
			subTotal = ventaCab.getMontoTotal().divide(IVA_10,2,BigDecimal.ROUND_HALF_UP);
			ventaCab.setSubTotalBigDecimal(subTotal);
			IVA = subTotal.multiply(new BigDecimal(0.1));
			ventaCab.setIva(IVA);
			
			mapVentaDetail.remove(uuid);
			session.setAttribute(MAP_DETALLE,mapVentaDetail);
			map.addAttribute(MAP_DETALLE,mapVentaDetail);
		}
		session.setAttribute(VENTA_CABECERA, ventaCab);
		map.addAttribute(VENTA_CABECERA, ventaCab);
		map.addAttribute("cantProducto",suma);
		
		return "venta/venta_detail";
	}
	
	

	@RequestMapping(value = "confirmar", method = RequestMethod.POST)
	public String confirmarVenta(ModelMap map, @Valid VentaCabecera ventaCabecera,HttpSession session) throws WebAppException {
		
		VentaCabecera ventaCab = null;
		VentaDetalle ventaDet = null;
		Caja caja = null;
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
		
		String nroComprobante = GeneralUtils.formatoComprobante(ventaCabecera.getId());
		ventaCabecera.setNroComprobante(nroComprobante);
		ventaCabeceraDao.createOrUpdate(ventaCabecera);
		
		
		for (VentaDetalle vd : mapaVentaDetalle.values()) {
			vd.setVentaCabecera(ventaCabecera);
			ventaDetalleDao.create(vd);
			disminuirStock(vd, map);
		}
		
		caja = new Caja();
		caja.setFecha(ventaCabecera.getFechaEmision());
		caja.setDescripcion("VENTA producto");
		caja.setEntradaBigDecimal(ventaCabecera.getMontoTotal());
		Caja cajaActual = cajaDao.findCajaByDate();
		
		if(cajaActual ==  null){
			throw new WebAppException("Debe abrir una caja antes de realizar una venta");
		}else{
			caja.setSaldoActual(cajaActual.getSaldoActual().add(ventaCabecera.getMontoTotal()));
		}
		caja.setFechaActual(new Date());
		caja.setFecha(ventaCabecera.getFechaEmision());
		caja.setSalidaBigDecimal(BigDecimal.ZERO);
		cajaDao.create(caja);
		
		map.addAttribute("msgExitoVenta", true);
		map.addAttribute("msgExito", "Venta creada con éxito");
		map.addAttribute("ventaCabeceraId", ventaCabecera.getIdVenta());
		session.setAttribute("ventaCabeceraId", ventaCabecera.getIdVenta());
		return indexPay(map, session);

	}
	
	@RequestMapping(value = "comprobante", method = RequestMethod.POST)
	public void comprobanteVenta(ModelMap map,HttpSession session,HttpServletRequest request,HttpServletResponse response,
			@RequestParam(required = true, value="id_venta_cabecera") Long idVentaCabecera) {
		String FOLDER = request.getSession().getServletContext().getRealPath("/reportes/");
		InputStream jasperStream = null;
		Map<String, Object> params = new HashMap<>();
		List<VentaDetalle> listdetalle = ventaDetalleDao.getListDetalle(idVentaCabecera);
		VentaCabecera ventaCabecera = ventaCabeceraDao.find(idVentaCabecera);
		
		params.put("LISTA_DETALLE", listdetalle);
		params.put("nombreCliente", ventaCabecera.getCliente().getNombre()+", "+ventaCabecera.getCliente().getApellido());
		params.put("TOTAL_A_PAGAR", ventaCabecera.getMontoTotal());
		params.put("RUC", ventaCabecera.getCliente().getRuc());
		params.put("nroVenta", ventaCabecera.getNroComprobante());
		Date d = new Date();
		String fechaActual = GeneralUtils.getStringFromDate(d,DATE_FORMAT);
		params.put("fechaSys", fechaActual);
		try {
			jasperStream = new FileInputStream(FOLDER + "/comprobante_venta.jasper");
		
		if (jasperStream != null) {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

			byte[] pdfReport = JasperExportManager.exportReportToPdf(jasperPrint);
			response.setContentType("application/x-pdf");
			response.setHeader("Content-disposition", "attachment; filename=venta_detalle.pdf");
			response.reset();
			response.setContentType("application/pdf");
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "private");
			response.setContentLength(pdfReport.length);
			response.getOutputStream().write(pdfReport);
			response.getOutputStream().flush();
			//response.getOutputStream().close();
		}
		
		} catch (JRException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@RequestMapping(value = "comprobante", method = RequestMethod.GET)
	public void comprobanteVentaGET(ModelMap map,HttpSession session,HttpServletRequest request,HttpServletResponse response,
			@RequestParam(required = true, value="id_venta") Long idVentaCabecera) {
		comprobanteVenta(map, session, request, response, idVentaCabecera);
	}
	
	
	@Override
	public Dao<VentaCabecera> getDao() {
		return ventaCabeceraDao;
	}
	
	private void disminuirStock(VentaDetalle ventaDet, ModelMap map){
		int resta = 0;
		//producto para disminuir el stok
		Producto productoDisminuir = null;
		//sumamos la cantidad del producto en stock, ya que se elimino del detalle
		productoDisminuir = productoDao.find(ventaDet.getProducto().getId());
		resta = productoDisminuir.getCantidad() - ventaDet.getCantidad();
		productoDisminuir.setCantidad(resta);
		productoDao.createOrUpdate(productoDisminuir);
		map.addAttribute("cantProducto",resta);
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

}