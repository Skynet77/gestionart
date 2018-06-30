package com.pol.gestionart.daoImpl;

import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.pol.gestionart.dao.UsuarioDao;
import com.pol.gestionart.security.Usuario;

@Repository
public class UsuarioDaoImpl extends DaoImpl<Usuario> implements UsuarioDao{

	@Override
	public String getCamposFiltrables() {
		return "estado||cedulaRuc||nombreRazonSocial||apellido";
	}
	
	@Override
	public Usuario buscar(String codigo) {

		String sql = "SELECT object(U) FROM Usuario AS U WHERE cedularuc = ?1";
		Query query = entityManager.createQuery(sql);
		query.setParameter(1, codigo);
		// query.setParameter(2, password);

		try {
			Usuario usuario = (Usuario) query.getSingleResult();
			logger.info("Se encontro el usuario {}'", codigo);
			return usuario;
		} catch (NoResultException e) {
			logger.info("No se encontro el usuario {}", codigo);
			return null;
		}

	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}

}