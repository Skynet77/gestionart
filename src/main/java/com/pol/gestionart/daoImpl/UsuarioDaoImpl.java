package com.pol.gestionart.daoImpl;

import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.pol.gestionart.dao.UsuarioDao;
import com.pol.gestionart.entity.Usuario;
import com.pol.gestionart.util.GeneralUtils;

@Repository
public class UsuarioDaoImpl extends DaoImpl<Usuario> implements UsuarioDao{

	@Override
	public String getCamposFiltrables() {
		return "estado||cedulaRuc||nombreRazonSocial||apellido";
	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuario getUsuarioByUserName(String cedula) {
		Usuario usuario = null;
		EntityManager em = entityManager;
		String sql = "select object(Usuario) from Usuario as Usuario";
		logger.debug("Realizando consulta: {}", sql);
		
		Query query = em.createQuery(sql);
		sql = sql + " WHERE cedularuc = ?1";
		query = entityManager.createQuery(sql);
		query.setParameter(1, cedula);
		try {
		
			usuario = (Usuario) query.getSingleResult();
			logger.info("Registros encontrados: {}", usuario);
			return usuario;
		
		} catch (NoResultException e) {
			return usuario;
		}
	}

}