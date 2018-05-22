package com.pol.gestionart.controller.form;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pol.gestionart.controller.list.CajaListController;
import com.pol.gestionart.controller.list.ClienteListController;
import com.pol.gestionart.dao.CajaDao;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.entity.Caja;
import com.pol.gestionart.entity.Cliente;



@Controller
@Scope("request")
@RequestMapping("movimiento")
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
	
}