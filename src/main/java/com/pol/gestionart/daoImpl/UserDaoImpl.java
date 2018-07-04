package com.pol.gestionart.daoImpl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.UserDao;
import com.pol.gestionart.entity.Usuario;

@Repository
public class UserDaoImpl extends DaoImpl<Usuario> implements UserDao{

	@Autowired 
	protected EntityManager entityManager;
	
	
	@Override
	public String getCamposFiltrables() {
		return "estado||cedulaRuc||nombreRazonSocial||apellido";
	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public com.pol.gestionart.entity.User findByUsername(String username) {
		String sql = "SELECT object(User) FROM User AS User ";
		sql = sql + "WHERE username = ?1";
		Query query = null;
		query = entityManager.createQuery(sql);
		query.setParameter(1, username);
		com.pol.gestionart.entity.User u = (com.pol.gestionart.entity.User) query.getSingleResult();
		return u;
	}



}