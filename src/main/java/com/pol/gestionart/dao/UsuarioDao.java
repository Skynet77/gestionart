package com.pol.gestionart.dao;

import com.pol.gestionart.entity.Usuario;

public interface UsuarioDao extends Dao<Usuario>{

	public Usuario getUsuarioByUserName(String cedula);
	
}
