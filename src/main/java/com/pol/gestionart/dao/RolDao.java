package com.pol.gestionart.dao;

import java.util.List;

import com.pol.gestionart.security.Rol;
import com.pol.gestionart.entity.Usuario;

public interface RolDao extends Dao<Rol> {
	public List<Rol> ListByUser(Usuario user);
	public Rol buscarRol(String codigo);
}