package com.pol.gestionart.controller.form;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pol.gestionart.controller.list.CajaListController;
import com.pol.gestionart.controller.list.VentaCabeceraListController;
import com.pol.gestionart.dao.CajaDao;
import com.pol.gestionart.dao.CajaReporteDao;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.dao.Descripcion_cajaDao;
import com.pol.gestionart.dao.InventarioDao;
import com.pol.gestionart.dao.ProductoDao;
import com.pol.gestionart.dao.VentaCabeceraDao;
import com.pol.gestionart.dao.VentaDetalleDao;
import com.pol.gestionart.entity.Caja;
import com.pol.gestionart.entity.DescripcionCaja;
import com.pol.gestionart.entity.Inventario;
import com.pol.gestionart.entity.Producto;
import com.pol.gestionart.entity.ReporteCaja;
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
@RequestMapping("caja")
public class CajaFormController extends FormController<Caja> {

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
	
	@Autowired
	private Descripcion_cajaDao descCajaDao;
	
	@Autowired
	private CajaReporteDao reporteCajaDao;
	
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
		List<DescripcionCaja> descCaja = descCajaDao.findEntities(true,-1,-1, "DescripcionCaja");
		map.addAttribute("acciones", descCaja);
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
	
	@RequestMapping("confirmar/{id_cabecera}")
	public String confirmarVentaGET(ModelMap map,HttpSession session, 
			@PathVariable(name="id_cabecera") Long idVentaCab) throws WebAppException {
		return confirmarVenta(map, session, idVentaCab, "confirmar");
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
				Date fechaEmision = GeneralUtils.getDateHours(ventaCabecera.getFechaEmision());
				String fec = GeneralUtils.getStringFromDate(fechaEmision, GeneralUtils.DATE_FORMAT_CAJA);
				caja.setFecha(fec);
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
				caja.setSalidaBigDecimal(BigDecimal.ZERO);
				cajaDao.create(caja);
				session.setAttribute("msgExito", "Venta confirmado con exito");
				
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
			session.setAttribute("msgExito", "Venta anulado con exito");
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
	
	
	@RequestMapping("cierre")
	public String cierreCaja(ModelMap map,HttpSession session) throws AjaxException {
		
		BigDecimal aperturaCaja = null;
		List<Caja> listCaja = cajaDao.findCajaForCierre();
		ReporteCaja reporte = new ReporteCaja();
		reporte.setTotalActual(BigDecimal.ZERO);
		reporte.setTotalEgreso(BigDecimal.ZERO);
		reporte.setTotalIngreso(BigDecimal.ZERO);

		if(listCaja.isEmpty()){
			throw new AjaxException("No hay movimientos en el día.");
		}
		for (Caja caja : listCaja) {
			reporte.setTotalIngreso(reporte.getTotalIngresoBigDecimal().add(caja.getEntrada()));
			reporte.setTotalEgreso(reporte.getTotalEgresoBigDecimal().add(caja.getSalida()));
			if("APERTURA".equalsIgnoreCase(caja.getDescripcion())){
				aperturaCaja = caja.getEntrada(); 
			}
			reporte.setFecha(caja.getFecha());
		}
		reporte.setApertura(aperturaCaja);
		BigDecimal totalParcial = reporte.getTotalIngresoBigDecimal().subtract(aperturaCaja);
		
		reporte.setTotalActual(totalParcial.subtract(reporte.getTotalEgresoBigDecimal()));
		reporteCajaDao.create(reporte);
		map.addAttribute("fecha",listCaja.get(0).getFecha());
		map.addAttribute("apertura",aperturaCaja);
		map.addAttribute("totalIngreso",reporte.getTotalIngreso());
		map.addAttribute("totalEgreso",reporte.getTotalEgreso());
		map.addAttribute("total",reporte.getTotalActual());
		map.addAttribute("idReporte",reporte.getId());
		
		//cerramos caja 
		Caja cerrarCaja = new Caja();
		cerrarCaja.setDescripcion("CIERRE");
		cerrarCaja.setEntradaBigDecimal(BigDecimal.ZERO);
		cerrarCaja.setFechaActual(new Date());
		cerrarCaja.setFecha(GeneralUtils.getStringFromDate(new Date(), GeneralUtils.DATE_FORMAT_CAJA));
		cerrarCaja.setSaldoActual(BigDecimal.ZERO);
		Caja cajaActual = cajaDao.findCajaByDate();
		cerrarCaja.setSalidaBigDecimal(cajaActual.getSaldoActual());
		cajaDao.create(cerrarCaja);
		
			
		
		return "caja/modal_reporte";
		
	}
	
	@RequestMapping(value = "comprobante/{id_reporte}", method = RequestMethod.GET)
	public void comprobanteVenta(ModelMap map,HttpSession session,HttpServletRequest request,HttpServletResponse response,
			@PathVariable(name="id_reporte") Long idReporte) {
		String FOLDER = request.getSession().getServletContext().getRealPath("/reportes/");
		InputStream jasperStream = null;
		Map<String, Object> params = new HashMap<>();
		ReporteCaja reporteCaja = reporteCajaDao.find(idReporte);
		List<Caja> listCaja = cajaDao.findCajaForCierre();
		
		params.put("LISTA_DETALLE", listCaja);
		params.put("totalEgreso", reporteCaja.getTotalEgreso());
		params.put("totalIngreso", reporteCaja.getTotalIngreso());
		params.put("totalActual", reporteCaja.getTotalActual());
		params.put("apertura", reporteCaja.getApertura());
		params.put("fecha", reporteCaja.getFecha());
		try {
			jasperStream = new FileInputStream(FOLDER + "/reporte_caja.jasper");
		
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
}