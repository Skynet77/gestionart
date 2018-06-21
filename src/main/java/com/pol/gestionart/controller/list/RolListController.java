package com.pol.gestionart.controller.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.dao.RolDao;
import com.pol.gestionart.security.Rol;

@Controller
@Scope("session")
@RequestMapping("rol")
public class RolListController extends ListController<Rol> {

	@Autowired
	RolDao rolDao;
	
	@Override
	public String[] getColumnas() {
		
		return new String[] { "id", "codigo", "descripcion" };
	}

	@Override
	public Dao<Rol> getDao() {
		
		return rolDao;
	}

}