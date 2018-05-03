package com.pol.gestionart.controller.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.dao.ProductoDao;
import com.pol.gestionart.entity.Producto;

@Controller
@Scope("session")
@RequestMapping("producto")
public class ProductoListController extends ListController<Producto> {
	
	@Autowired
	private ProductoDao productoDao;

	@Override
	public String[] getColumnas() {
		return new String[] {"id","codigo","tipoProducto", "familia", "descripcion", "precioCompra", "precioVenta","cantidad" };
	}

	@Override
	public Dao<Producto> getDao() {
		return productoDao;
	}

	
}
