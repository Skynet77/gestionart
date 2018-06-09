package com.pol.gestionart.controller.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pol.gestionart.controller.list.InventarioListController;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.dao.InventarioDao;
import com.pol.gestionart.entity.Inventario;

@Controller
@Scope("request")
@RequestMapping("inventario")
public class InventarioFormController extends FormController<Inventario> {

	@Autowired
	private InventarioDao inventarioDao;
	

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
	
}