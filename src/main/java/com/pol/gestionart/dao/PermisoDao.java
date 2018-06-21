package com.pol.gestionart.dao;

import java.util.List;

import com.pol.gestionart.security.Permiso;
import com.pol.gestionart.security.Rol;

public interface PermisoDao extends Dao<Permiso> {
	List<Permiso> ListByRol(Rol rol);
}