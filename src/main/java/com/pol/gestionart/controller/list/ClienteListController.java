package com.pol.gestionart.controller.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pol.gestionart.dao.ClienteDao;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.entity.Cliente;


@Controller
@Scope("session")
@RequestMapping("cliente")
public class ClienteListController extends ListController<Cliente> {

	@Autowired
	private ClienteDao clienteDao;

	@Override
	public String[] getColumnas() {
		return new String[] { "id", "nombre", "apellido", "ruc", "nroTelefono", "correoElectronico","estado" };
	}

	@Override
	public Dao<Cliente> getDao() {
		return clienteDao;
	}
}
