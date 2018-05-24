package com.pol.gestionart.daoImpl;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.CompraDetalleDao;
import com.pol.gestionart.entity.CompraDetalle;

@Repository
public class CompraDetalleDaoImpl extends DaoImpl<CompraDetalle> implements CompraDetalleDao{

	@Override
	public String getCamposFiltrables() {
		return "estado";
	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<CompraDetalle> getListDetalle(Long idCabeceraDetalle){
		logger.info("Obteniendo lista de objetos, sSearch: {}", idCabeceraDetalle);
		
		String sql = "SELECT object(#ENTITY#) FROM #ENTITY# AS #ENTITY# ";
		sql = sql.replace("#ENTITY#", getEntityName());
		Query query = null;
		if (idCabeceraDetalle== null || "".equals(idCabeceraDetalle)) {
			query = entityManager.createQuery(sql);
		} else {
			sql = sql + "WHERE compracabecera_id = ?1";
			query = entityManager.createQuery(sql);
			query.setParameter(1, idCabeceraDetalle);
		}
		List<CompraDetalle> list = query.getResultList();
		logger.info("Cantidad de registros encontrados: {}",list);
		return list;		
	}

}