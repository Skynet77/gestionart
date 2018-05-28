package com.pol.gestionart.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.CajaDao;
import com.pol.gestionart.entity.Caja;

@Repository
public class CajaDaoImpl extends DaoImpl<Caja> implements CajaDao{
	private static final String UNCHECKED = "unchecked";
	
	@Override
	public String getCamposFiltrables() {
		return "fecha||descripcion";
	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	@Transactional
	@SuppressWarnings(UNCHECKED)
	public Caja findCajaByDate(String fecha) {

		EntityManager em = entityManager;
		String sql = "select object(ENTITY) from ENTITY as ENTITY";
		sql = sql.replace("ENTITY", getEntityName());
		logger.debug("Realizando consulta: {}", sql);

		Query query = em.createQuery(sql);
		sql = sql + "WHERE fecha = (?1)";
		query = entityManager.createQuery(sql);
		query.setParameter(1,fecha);
		Caja caja = (Caja) query.getSingleResult();
		logger.info("Registros encontrados: {}", caja);
		return caja;
	}
	
	

}