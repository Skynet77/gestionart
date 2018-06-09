package com.pol.gestionart.controller.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.dao.InventarioDao;
import com.pol.gestionart.entity.Inventario;

@Controller
@Scope("session")
@RequestMapping("inventario")
public class InventarioListController extends ListController<Inventario> {

	@Autowired
	private InventarioDao inventarioDao;

	@Override
	public String[] getColumnas() {
		return new String[] { "id", "familia.codigo", "producto.nombre", "caja.descripcion", "compraDetalle.cantidad" };
	}

	@Override
	public Dao<Inventario> getDao() {
		return inventarioDao;
	}
}