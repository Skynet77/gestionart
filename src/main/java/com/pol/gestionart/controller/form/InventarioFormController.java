package com.pol.gestionart.controller.form;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

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
			@RequestParam(value = "mes") int mesFe,
			@RequestParam(value = "id_inv") Long idInventario) {
		agregarValoresAdicionales(map);
		List<InventarioDetalle> inventarioDetalle = inventarioDao.joinInventario(idProd, mesFe);
		Inventario inventario = inventarioDao.find(idInventario);
		map.addAttribute("listDetalle", inventarioDetalle);
		map.addAttribute("inventario", inventario);
		map.addAttribute("idProR", idProd);
		map.addAttribute("mesR", mesFe);
		map.addAttribute("idInvenR", idInventario);
		return "inventario/modal_inventario";
	}
	
	@RequestMapping(value = "reporte/{idInvenR}/{idProdR}/{mesR}", method = RequestMethod.GET)
	public String reporteInventario(ModelMap map, HttpSession session,HttpServletRequest request,HttpServletResponse response,
			@PathVariable(name="idProdR") Long idProd,
			@PathVariable(name="mesR") int mesFe,
			@PathVariable(name="idInvenR") Long idInventario) {
		
		if(session.getAttribute("usuariologin") ==null) {
			return "redirect:/login";
		}
		
		agregarValoresAdicionales(map);
		List<InventarioDetalle> inventarioDetalle = inventarioDao.joinInventario(idProd, mesFe);
		Inventario inventario = inventarioDao.find(idInventario);
		String FOLDER = request.getSession().getServletContext().getRealPath("/reportes/");
		InputStream jasperStream = null;
		Map<String, Object> params = new HashMap<>();
		
		params.put("LISTA_DETALLE", inventarioDetalle);
		params.put("fechaMes", inventario.getFechaMes());
		params.put("nombreProducto", inventario.getProducto().getDescripcion());
		params.put("stockInicial", inventario.getStockInicial());
		params.put("entrada", inventario.getEntrada());
		params.put("salida", inventario.getSalida());
		params.put("stockActual", inventario.getActual());
		
		try {
			jasperStream = new FileInputStream(FOLDER + "/inventario.jasper");
		
		if (jasperStream != null) {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

			byte[] pdfReport = JasperExportManager.exportReportToPdf(jasperPrint);
			response.setContentType("application/x-pdf");
			response.setHeader("Content-disposition", "attachment; filename=inventario.pdf");
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

		
		
		return "inventario/modal_inventario";
	}

	@RequestMapping(value = "ajuste_inventario", method = RequestMethod.GET)
	public String ajusteInventario(ModelMap map, HttpSession session) {
		agregarValoresAdicionales(map);
		
		if(session.getAttribute("usuariologin") ==null) {
			return "redirect:/login";
		}

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
		}else if(inventarioMesAnterio == null) {
			throw new AjaxException("No existen registros de inventario en ese mes para el producto!");
		}
		inventarioDao.createOrUpdate(inventarioMesAnterio);
		session.setAttribute("msgAjuste", "Ajuste realizado con éxito!");
		} catch (AjaxException e) {
			throw new AjaxException(e.getErrorBody());
		}
		 catch (Exception e) {
			 throw new AjaxException("Ocurrió un error al intentar realizar el ajuste");
		 }
		return inv;
	}

}