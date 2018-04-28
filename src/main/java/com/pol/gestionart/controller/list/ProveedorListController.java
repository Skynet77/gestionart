package com.pol.gestionart.controller.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pol.gestionart.dao.ProveedorDao;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.entity.Proveedor;

@Controller
@Scope("session")
@RequestMapping("proveedor")
public class ProveedorListController extends ListController<Proveedor> {

	@Autowired
	private ProveedorDao proveedorDao;

	@Override
	public String[] getColumnas() {
		return new String[] { "id", "nombre", "ruc", "nombre_contacto", "nroTelefono", "correoElectronico", "direccion" };
	}

	@Override
	public Dao<Proveedor> getDao() {
		return proveedorDao;
	}
}