package com.pol.gestionart.controller.form;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pol.gestionart.controller.list.CompraCabeceraListcontroller;
import com.pol.gestionart.dao.CajaDao;
import com.pol.gestionart.dao.CompraCabeceraDao;
import com.pol.gestionart.dao.CompraDetalleDao;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.dao.InventarioDao;
import com.pol.gestionart.dao.ProductoDao;
import com.pol.gestionart.dao.ProveedorDao;
import com.pol.gestionart.entity.Caja;
import com.pol.gestionart.entity.CompraCabecera;
import com.pol.gestionart.entity.CompraDetalle;
import com.pol.gestionart.entity.Inventario;
import com.pol.gestionart.entity.Producto;
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
@RequestMapping("compra")
public class CompraFormController extends FormController<CompraCabecera> {
	public static final String LISTA_DETALLE = "listaDetalle";
	public static final String MAP_DETALLE_COMPRA = "mapDetalleCompra";
	public static final String LISTA_PRODUCTO_COMPRA = "listaProductoCompra";
	public static final String COMPRA_CABECERA = "compraCabecera";
	public static final String COMPRA_DETALLE = "compraDetalle";
	public static final String PRODUCTO_DETALLE = "productoDetalle";
	public static final BigDecimal IVA_10 = new BigDecimal(1.1);
	private static final String DATE_FORMAT = "dd/MM/yyyy-HH:mm:ss";
	
	@Autowired 
	private CompraCabeceraDao compraCabeceraDao;
	
	@Autowired
	private CompraCabeceraListcontroller compraCabeceraList;
	
	@Autowired 
	private ProveedorDao proveedorDao;
	
	@Autowired
	private CompraDetalleDao compraDetalleDao;
	
	@Autowired 
	private ProductoDao productoDao;

	@Autowired 
	private CajaDao cajaDao;
	
	@Autowired
	private InventarioDao inventarioDao;
	
	@Override
	public String getTemplatePath() {
		return "compra/compra_index";
	}

	@Override
	public String getNombreObjeto() {
		return "compraCabecera";
	}

	@Override
	public CompraCabecera getNuevaInstancia() {
		return new CompraCabecera();
	}
	
	@Override
	public void agregarValoresAdicionales(ModelMap map) {
		map.addAttribute("columnas", compraCabeceraList.getColumnas());
		map.addAttribute("columnasStr", compraCabeceraList.getColumnasStr(null));
		map.addAttribute("proveedorList", getDao().getList(0, 100, null));
		map.addAttribute("productoList", getDao().getList(0, 100, null));
		map.addAttribute("compraCabecera", new CompraCabecera());
		map.addAttribute("tituloFormulario", "Registrar Compra");
		map.addAttribute("accion", "guardar");
		super.agregarValoresAdicionales(map);
	}
	
	@RequestMapping("buy")
	public String indexPay(ModelMap map,HttpSession session) {
		
		map.addAttribute(getNombreObjeto(), getNuevaInstancia());
		agregarValoresAdicionales(map);
		session.setAttribute(MAP_DETALLE_COMPRA,null);
		session.setAttribute(COMPRA_CABECERA, null);
		session.setAttribute(COMPRA_DETALLE, null);
		return "compra/compra_pay"; 
	}
	
		
		@RequestMapping(value = "agregar-detalle", method = RequestMethod.POST)
		public String addProducto(ModelMap map,
				@RequestParam(required = true, value="id_producto") Long idProducto, 
				@RequestParam(required = true, value="cantidad") int cantidad, HttpSession session) throws AjaxException {
			
			List<Producto> listProducto = null;
			CompraCabecera compraCab = null;
			CompraDetalle compraDet = null;
			List<CompraDetalle> listDetalle = null;
			Producto producto = null;
			BigDecimal montoTotal;
			try {
				if(session.getAttribute(LISTA_PRODUCTO_COMPRA)!=null){
					listProducto = (List<Producto>) session.getAttribute(LISTA_PRODUCTO_COMPRA);
				}else{
					listProducto = new ArrayList<>();
				}
				
				
				if(session.getAttribute(COMPRA_CABECERA)!=null){
					compraCab = (CompraCabecera) session.getAttribute(COMPRA_CABECERA);
				}else{
					compraCab = new CompraCabecera();
				}
				
				producto = productoDao.find(idProducto);
				
				if(producto != null){
					listProducto.add(producto);
				}
				//obtenemos el monto total de la cabecera
				if(compraCab.getTotal()!=null){
					montoTotal = compraCab.getTotal();
				}else{
					montoTotal = new BigDecimal(0);
				}
				
				//multiplicamos el precio de compra por la cantidad
				BigDecimal montoCompra = producto.getPrecioCompra().multiply(new BigDecimal(cantidad));
				//sumamos el monto total que teniamos por 
				montoTotal = montoTotal.add(montoCompra);
				//volvemos a guardar el monto total
				compraCab.setTotalBigDecimal(montoTotal);
				
				
				//calculo de subTotal
				BigDecimal subTotal;
				BigDecimal IVA;
				if(compraCab.getSubTotal()!=null){
					subTotal = compraCab.getSubTotal();
				}else{
					subTotal = new BigDecimal(0);
				}
				subTotal = compraCab.getTotal().divide(IVA_10,2,BigDecimal.ROUND_HALF_UP);
				compraCab.setSubTotalBigDecimal(subTotal);
				IVA = subTotal.multiply(new BigDecimal(0.1));
			//	compraCab.setIva(IVA);
				
				//el detalle actual que se esta procesando
				compraDet = new CompraDetalle();
				compraDet.setCantidad(cantidad);
				compraDet.setPrecioTotal(montoCompra);
				compraDet.setPrecioUnitario(producto.getPrecioCompra());
				compraDet.setProducto(producto);
				compraDet.setCompraCabecera(compraCab);
				
				
			} catch (Exception e ) {
				throw new AjaxException("Ocurrió un error inesperado");
			}
			
			Map<String, CompraDetalle> mapaCompraDetalle = GeneralUtils.mapSerializeCompraDetalleOrUpdate(session,compraDet);
			session.setAttribute(MAP_DETALLE_COMPRA,mapaCompraDetalle);
			session.setAttribute(COMPRA_CABECERA, compraCab);
			session.setAttribute(COMPRA_DETALLE, compraDet);
			map.addAttribute(MAP_DETALLE_COMPRA,mapaCompraDetalle);
			map.addAttribute(COMPRA_CABECERA, compraCab);
			map.addAttribute(COMPRA_DETALLE, compraDet);
			map.addAttribute(PRODUCTO_DETALLE, producto);
			
			
			return "compra/compra_detail";

		}
		
		@RequestMapping(value = "eliminar-detalle", method = RequestMethod.POST)
		public String deleteProducto(ModelMap map,
				@RequestParam(required = true, value="id_producto") String uuid,  HttpSession session) {
			Map<String, CompraDetalle> mapCompraDetail = new HashMap<>();
			CompraCabecera compraCab = null;
			CompraDetalle compraDet = null;
			int resta = 0;
			if(session.getAttribute(COMPRA_CABECERA)!=null){
				compraCab = (CompraCabecera) session.getAttribute(COMPRA_CABECERA);
			}else{
				compraCab = new CompraCabecera();
			}
			
			if(uuid!=null){
				mapCompraDetail = (Map<String, CompraDetalle>) session.getAttribute(MAP_DETALLE_COMPRA);
				compraDet = mapCompraDetail.get(uuid);
				compraCab.setTotalBigDecimal(compraCab.getTotal().subtract(compraDet.getPrecioTotal()));
				
				//calculo de subTotal
				BigDecimal subTotal;
				BigDecimal IVA;
				subTotal = compraCab.getTotal().divide(IVA_10,2,BigDecimal.ROUND_HALF_UP);
				compraCab.setSubTotalBigDecimal(subTotal);
				IVA = subTotal.multiply(new BigDecimal(0.1));
				//compraCab.setIva(IVA);
				
				mapCompraDetail.remove(uuid);
				session.setAttribute(MAP_DETALLE_COMPRA,mapCompraDetail);
				map.addAttribute(MAP_DETALLE_COMPRA,mapCompraDetail);
			}
			session.setAttribute(COMPRA_CABECERA, compraCab);
			map.addAttribute(COMPRA_CABECERA, compraCab);
			
			return "compra/compra_detail";
		}
		
		
		
		@RequestMapping(value = "confirmar", method = RequestMethod.POST)
		public String confirmarCompra(ModelMap map, CompraCabecera compraCabecera,HttpSession session) throws WebAppException {
			
			CompraCabecera compraCab = null;
			CompraDetalle compraDet = null;
			Map<String, CompraDetalle> mapaCompraDetalle = null;
			Caja cajaActual = cajaDao.findCajaByDate();
			
			if(cajaActual ==  null){
				throw new WebAppException("Debe abrir una caja antes de realizar una compra");
			}
			
			if(session.getAttribute(MAP_DETALLE_COMPRA)!=null){
				mapaCompraDetalle  = (Map<String, CompraDetalle>) session.getAttribute(MAP_DETALLE_COMPRA);
			}

			compraCabecera.setNroComprobante("1");
			compraCabeceraDao.create(compraCabecera);
			
			String nroComprobante = GeneralUtils.formatoComprobante(compraCabecera.getId());
			compraCabecera.setNroComprobante(nroComprobante);
			compraCabeceraDao.createOrUpdate(compraCabecera);
			
			
			double porcentaje= 0.4;
	        BigDecimal b = BigDecimal.valueOf(porcentaje); 
	        		//new BigDecimal(porcentaje, MathContext.DECIMAL64);
			BigDecimal recargo;
			
			
			Inventario inventario = null;
			Inventario inventarioMesAnterio = null;
			for (CompraDetalle vd : mapaCompraDetalle.values()) {
				inventario = inventarioDao.getInventarioByProductoFecha(vd.getProducto().getId(),null);
				if(inventario == null){
					Calendar calendar = Calendar.getInstance();
					Date d = new Date(calendar.getTimeInMillis());
					int mes = d.getMonth();
					inventarioMesAnterio = inventarioDao.getInventarioByProductoFecha(vd.getProducto().getId(),mes);
				}
				//verificamos si el mes anterior quedo algo en el stock
				if(inventario == null){
					inventario = new Inventario();
					
					inventario.setEntrada(vd.getCantidad());
					inventario.setFechaMes(GeneralUtils.getDate(compraCabecera.getFechaCompra()));
					inventario.setProducto(vd.getProducto());
					inventario.setSalida(0);
					if(inventarioMesAnterio!=null){
						inventario.setStockInicial(inventarioMesAnterio.getActual()+vd.getCantidad());
						inventario.setActual(inventarioMesAnterio.getActual()+vd.getCantidad());
					}else{
						inventario.setStockInicial(vd.getCantidad());
						inventario.setActual(vd.getCantidad());
					}
				}else{
					inventario.setActual(inventario.getActual()+vd.getCantidad());
					inventario.setEntrada(inventario.getEntrada()+vd.getCantidad());
					inventario.setFechaMes(GeneralUtils.getDate(compraCabecera.getFechaCompra()));
					inventario.setProducto(vd.getProducto());
					inventario.setSalida(0);
				}
				Producto producto = vd.getProducto();
				recargo = producto.getPrecioCompra().multiply(b);
				producto.setPrecioVentaBigDecimal(producto.getPrecioCompra().add(recargo));
				productoDao.createOrUpdate(producto);
				vd.setCompraCabecera(compraCabecera);
				aumentarStock(vd.getProducto(), vd, map);
				inventarioDao.createOrUpdate(inventario);
				compraDetalleDao.create(vd);
			}
			
			Caja caja = new Caja();
			caja.setFecha(compraCabecera.getFechaCompra());
			caja.setDescripcion("COMPRA PRODUCTO");
			caja.setSalidaBigDecimal(compraCabecera.getTotal());
			caja.setSaldoActual(cajaActual.getSaldoActual().subtract(compraCabecera.getTotal()));
			
			caja.setFechaActual(new Date());
			caja.setEntradaBigDecimal(BigDecimal.ZERO);
			cajaDao.create(caja);
			map.addAttribute("msgExitoCompra", true);
			map.addAttribute("compraCabeceraId", compraCabecera.getId());
			session.setAttribute("compraCabeceraId", compraCabecera.getId());
			return indexPay(map, session);
		}
		
		@RequestMapping(value = "comprobante", method = RequestMethod.POST)
		public void comprobanteCompra(ModelMap map,HttpSession session,HttpServletRequest request,HttpServletResponse response,
				@RequestParam(required = true, value="id_compra_cabecera") Long idCompraCabecera) {
			String FOLDER = request.getSession().getServletContext().getRealPath("/reportes/");
			InputStream jasperStream = null;
			Map<String, Object> params = new HashMap<>();
			List<CompraDetalle> listdetalle = compraDetalleDao.getListDetalle(idCompraCabecera);
			CompraCabecera compraCabecera = compraCabeceraDao.find(idCompraCabecera);
			
			params.put("LISTA_DETALLE", listdetalle);
			params.put("nombreProveedor", compraCabecera.getProveedor().getNombre()+", "+compraCabecera.getProveedor().getRuc());
			params.put("TOTAL_A_PAGAR", compraCabecera.getTotal());
			params.put("RUC", compraCabecera.getProveedor().getRuc());
			params.put("nroCompra", compraCabecera.getNroComprobante());
			Date d = new Date();
			String fechaActual = GeneralUtils.getStringFromDate(d,DATE_FORMAT);
			params.put("fechaSys", fechaActual);
			try {
				jasperStream = new FileInputStream(FOLDER + "/comprobante_compra.jasper");
			
			if (jasperStream != null) {
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

				byte[] pdfReport = JasperExportManager.exportReportToPdf(jasperPrint);
				response.setContentType("application/x-pdf");
				response.setHeader("Content-disposition", "attachment; filename=compra_detalle.pdf");
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
		public void comprobanteCompraGET(ModelMap map,HttpSession session,HttpServletRequest request,HttpServletResponse response,
				@RequestParam(required = true, value="id_compra") Long idCompraCabecera) {
			comprobanteCompra(map, session, request, response, idCompraCabecera);
		}
		
	@Override
	public Dao<CompraCabecera> getDao() {
		return compraCabeceraDao;
	}
	
	

	private void disminuirStock(CompraDetalle compraDet, ModelMap map){
		int resta = 0;
		//producto para disminuir el stok
		Producto productoDisminuir = null;
		//sumamos la cantidad del producto en stock, ya que se elimino del detalle
		productoDisminuir = compraDet.getProducto();
		resta = productoDisminuir.getCantidad() - compraDet.getCantidad();
		productoDisminuir.setCantidad(resta);
		productoDao.createOrUpdate(productoDisminuir);
		map.addAttribute("cantProducto",resta);
	}
	
	private void aumentarStock(Producto producto, CompraDetalle compraDet, ModelMap map){
		/*AUMENTAMOS EL STOCK*/
		
		Producto productoAumentar = null;
		int sumar = 0;
		//aumentamos la cantidad del producto en stock
		productoAumentar = producto;
		sumar = productoAumentar.getCantidad() + compraDet.getCantidad();
		productoAumentar.setCantidad(sumar);
		productoDao.createOrUpdate(productoAumentar);
	}


}
