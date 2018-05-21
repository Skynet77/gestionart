package com.pol.gestionart.controller.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pol.gestionart.dao.CajaDao;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.entity.Caja;


@Controller
@Scope("session")
@RequestMapping("caja")
public class CajaListController extends ListController<Caja> {

	@Autowired
	private CajaDao cajaDao;

	@Override
	public String[] getColumnas() {
		return new String[] { "id", "fecha", "descripcion", "entrada", "salida", "saldo_actual" };
	}

	@Override
	public Dao<Caja> getDao() {
		return cajaDao;
	}
}