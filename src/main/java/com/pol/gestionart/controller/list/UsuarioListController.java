package com.pol.gestionart.controller.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.dao.UsuarioDao;
import com.pol.gestionart.entity.Usuario;

@Controller
@Scope("session")
@RequestMapping("usuario")
public class UsuarioListController extends ListController<Usuario> {

	@Autowired
	private UsuarioDao usuarioDao;

	@Override
	public String[] getColumnas() {
		return new String[] { "id","cedulaRuc", "nombreRazonSocial", "apellido","estado" };
	}

	@Override
	public Dao<Usuario> getDao() {
		return usuarioDao;
	}
}