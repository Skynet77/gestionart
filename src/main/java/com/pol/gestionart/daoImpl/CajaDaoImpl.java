package com.pol.gestionart.daoImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.CajaDao;
import com.pol.gestionart.entity.Caja;
import com.pol.gestionart.util.GeneralUtils;

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
	public Caja findCajaByDate() {
		Caja caja =  null;
		EntityManager em = entityManager;
		String sql = "select object(ENTITY) from ENTITY as ENTITY";
		sql = sql.replace("ENTITY", getEntityName());
		logger.debug("Realizando consulta: {}", sql);
		int anho = 0, mes = 0, dia = 0;
		
		Calendar calendar = Calendar.getInstance();
		Date d = new Date(calendar.getTimeInMillis());
		String fechaActual = GeneralUtils.getStringFromDate(d,GeneralUtils.DATE_FORMAT_CAJA);
		Query query = em.createQuery(sql);
		sql = sql + " WHERE fechaactual = (select MAX(fechaActual) from Caja ) and fecha = ?1 and descripcion != ?2";
		query = entityManager.createQuery(sql);
		query.setParameter(1, fechaActual);
		query.setParameter(2, "CIERRE");
		try {
		
		caja = (Caja) query.getSingleResult();
			logger.info("Registros encontrados: {}", caja);
			return caja;
		
		} catch (NoResultException e) {
			return caja;
		}
		
	}
	
	@Override
	@Transactional
	@SuppressWarnings(UNCHECKED)
	public List<Caja> findCajaForCierre() {
		List<Caja> cajaList =  null;
		EntityManager em = entityManager;
		String sql = "select object(ENTITY) from ENTITY as ENTITY";
		sql = sql.replace("ENTITY", getEntityName());
		logger.debug("Realizando consulta: {}", sql);
		int anho = 0, mes = 0, dia = 0;
		
		Calendar calendar = Calendar.getInstance();
		Date d = new Date(calendar.getTimeInMillis());
		String fechaActual = GeneralUtils.getStringFromDate(d,GeneralUtils.DATE_FORMAT_CAJA);
		Query query = em.createQuery(sql);
		sql = sql + " WHERE fecha = ?1 order by fechaactual asc";
		query = entityManager.createQuery(sql);
		query.setParameter(1, fechaActual);
		try {
		
		cajaList = query.getResultList();
			logger.info("Registros encontrados: {}", cajaList);
			return cajaList;
		
		} catch (NoResultException e) {
			return cajaList;
		}
		
	}
	
	
	
	

}