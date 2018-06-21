package com.pol.gestionart.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.PermisoDao;
import com.pol.gestionart.security.Permiso;
import com.pol.gestionart.security.Rol;
import com.pol.gestionart.security.RolPermiso;

@Repository
@Scope("request")
public class PermisoDaoImpl extends DaoImpl<Permiso> implements PermisoDao {

	@Autowired
	protected EntityManager entityManager;

	@Override
	public String getCamposFiltrables() {
		return "codigo||descripcion";
	}

	@Override
	public List<Permiso> ListByRol(Rol rol) {

		if (rol == null) {
			return new ArrayList<>();
		}
		logger.info("Obteniendo permisos de rol: {}", rol);
		List<Permiso> listPermiso = new ArrayList<>();
		/*
		 * String sql = "SELECT object(Permiso) " + "FROM Permiso AS Permiso " +
		 * "WHERE id IN (SELECT permiso_id FROM RolPermiso WHERE rol_id = ?1)";
		 */
		String sql = "SELECT object(RolPermiso) " + "FROM RolPermiso AS RolPermiso WHERE rol_id = ?1";

		Query query = null;
		query = entityManager.createQuery(sql);
		query.setParameter(1, rol.getId());

		@SuppressWarnings("unchecked")
		List<RolPermiso> rolPermisoList = query.getResultList();
		for (RolPermiso rolpermiso : rolPermisoList) {
			listPermiso.add(rolpermiso.getPermiso());
		}

		logger.info("Cantidad de permisos obtenidos: {}", listPermiso.size());
		return listPermiso;
	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}

}