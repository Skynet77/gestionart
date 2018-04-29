package com.pol.gestionart.controller.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pol.gestionart.dao.VentaCabeceraDao;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.entity.VentaCabecera;


@Controller
@Scope("session")
@RequestMapping("venta")
public class VentaCabeceraListController extends ListController<VentaCabecera> {

	@Autowired
	private VentaCabeceraDao ventaDao;

	@Override
	public String[] getColumnas() {
		return new String[] { "id", "nombre", "total" };
	}

	@Override
	public Dao<VentaCabecera> getDao() {
		return ventaDao;
	}
}
