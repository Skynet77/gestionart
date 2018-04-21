package com.pol.gestionart.daoImpl;

import org.springframework.stereotype.Repository;

import java.util.List;

import com.pol.gestionart.dao.UsuarioDao;
import com.pol.gestionart.entity.Usuario;

@Repository
public class UsuarioDaoImpl extends DaoImpl<Usuario> implements UsuarioDao{

	@Override
	public String getCamposFiltrables() {
		return "estado";
	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}

}