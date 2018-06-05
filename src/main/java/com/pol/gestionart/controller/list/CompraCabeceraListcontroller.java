package com.pol.gestionart.controller.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pol.gestionart.dao.CompraCabeceraDao;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.entity.CompraCabecera;

@Controller
@Scope("session")
@RequestMapping("compra")
public class CompraCabeceraListcontroller extends ListController<CompraCabecera> {
	
	@Autowired
	private CompraCabeceraDao compraCabeceraDao;

	@Override
	public Dao<CompraCabecera> getDao() {
		return compraCabeceraDao;
	}

	@Override
	public String[] getColumnas() {
		return new String[] { "id", "proveedor.id", "nroFactura", "fechaCompra", "total", "nroComprobante" };
	}

}
