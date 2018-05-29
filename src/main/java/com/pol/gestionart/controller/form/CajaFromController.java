package com.pol.gestionart.controller.form;


import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pol.gestionart.controller.list.CajaListController;
import com.pol.gestionart.controller.list.ClienteListController;
import com.pol.gestionart.dao.CajaDao;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.entity.Caja;
import com.pol.gestionart.entity.Cliente;
import com.pol.gestionart.util.GeneralUtils;



@Controller
@Scope("request")
@RequestMapping("caja")
public class CajaFromController extends FormController<Caja> {

	@Autowired
	private CajaDao cajaDao;
	
	@Autowired
	private CajaListController cajaList;

	
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
		map.addAttribute("columnas", cajaList.getColumnas());
		map.addAttribute("columnasStr", cajaList.getColumnasStr(null));
		map.addAttribute("cajaList", getDao().getList(0, 100, null));
		map.addAttribute("caja", new Caja());
		map.addAttribute("tituloFormulario", "Registrar Caja");
		map.addAttribute("accion", "guardar");
		super.agregarValoresAdicionales(map);
	}
	
	@RequestMapping("registro")
	public String ingreso(ModelMap map,HttpSession session, Caja caja) {
		Date d = new Date();
		String fechaActual = GeneralUtils.getStringFromDate(d,GeneralUtils.DATE_FORMAT_CAJA);
		Caja addCaja = new Caja();
		Caja cajaActual = cajaDao.findCajaByDate(fechaActual);
		BigDecimal  saldoActual = BigDecimal.ZERO; 
		
		if(!(caja.getEntrada()==null)){
			addCaja.setDescripcion(caja.getDescripcion());
			addCaja.setEntrada(caja.getEntrada());
			addCaja.setFecha(fechaActual);
			
			saldoActual  = cajaActual.getSaldoActual().add(caja.getEntrada());
			addCaja.setSaldoActual(saldoActual);
			addCaja.setSalida(BigDecimal.ZERO);
		}else if(!(caja.getSalida()==null)){
			addCaja.setDescripcion(caja.getDescripcion());
			addCaja.setEntrada(caja.getEntrada());
			addCaja.setFecha(fechaActual);
			
			saldoActual  = cajaActual.getSaldoActual().subtract(caja.getEntrada());
			addCaja.setSaldoActual(saldoActual);
			addCaja.setSalida(BigDecimal.ZERO);
		}
		
		return getTemplatePath(); 
	}
	
}