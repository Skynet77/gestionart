package com.pol.gestionart.controller.form;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pol.gestionart.controller.list.CajaListController;
import com.pol.gestionart.dao.CajaDao;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.entity.Caja;
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
	
}