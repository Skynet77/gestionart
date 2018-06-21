package com.pol.gestionart.dao;

import java.util.List;

import com.pol.gestionart.security.RolPermiso;

public interface RolPermisoDao extends Dao<RolPermiso> {

	public RolPermiso eliminarPorPermiso(Long id_rol, Long id_permiso);
	public List<RolPermiso> eliminarPorRol(Long id_rol);
}