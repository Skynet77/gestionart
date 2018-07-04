package com.pol.gestionart.daoImpl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.ClienteDao;
import com.pol.gestionart.entity.Cliente;

@Repository
public class ClienteDaoImpl extends DaoImpl<Cliente> implements ClienteDao{

	@Override
	public String getCamposFiltrables() {
		return "estado||apellido||nombre||ruc";
	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getCantidadCliente() {
		logger.info("Obteniendo la cantidad de clientes");
		Long total = (long) 0;
		try {
			String sql = "SELECT count(*) FROM #ENTITY# AS #ENTITY# ";
			sql = sql.replace("#ENTITY#", "Cliente");
			Query query = null;
			query = entityManager.createQuery(sql);
			total = (Long) query.getSingleResult();	
		} catch (Exception e) {
			total = (long) 0;
		}
		return total;
	}

}
