package com.pol.gestionart.daoImpl;

import java.util.Calendar;
import java.util.Date;

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
		//calendar.set(Calendar.DATE, 1);
		Date d = new Date(calendar.getTimeInMillis());
		String fechaActual = GeneralUtils.getStringFromDate(d,GeneralUtils.DATE_FORMAT_CAJA);
		Query query = em.createQuery(sql);
		sql = sql + " WHERE fechaactual = (select MAX(fechaActual) from Caja ) and fecha = ?1";
		query = entityManager.createQuery(sql);
		query.setParameter(1, fechaActual);
	//	query.setParameter(1,fecha);
		try {
		
		caja = (Caja) query.getSingleResult();
//		if(caja.getFechaActual().compareTo(new Date())>1 ){
			logger.info("Registros encontrados: {}", caja);
			return caja;
//		}else{
//			return null;
//		}
		
		} catch (NoResultException e) {
			return caja;
		}
		
	}
	
	

}