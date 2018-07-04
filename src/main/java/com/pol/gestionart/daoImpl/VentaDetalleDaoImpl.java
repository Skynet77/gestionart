package com.pol.gestionart.daoImpl;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.VentaDetalleDao;
import com.pol.gestionart.entity.VentaDetalle;

@Repository
public class VentaDetalleDaoImpl extends DaoImpl<VentaDetalle> implements VentaDetalleDao{

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
	public List<VentaDetalle> getListDetalle(Long idCabeceraDetalle){
		logger.info("Obteniendo lista de objetos, sSearch: {}", idCabeceraDetalle);
		
		String sql = "SELECT object(#ENTITY#) FROM #ENTITY# AS #ENTITY# ";
		sql = sql.replace("#ENTITY#", getEntityName());
		Query query = null;
		if (idCabeceraDetalle== null || "".equals(idCabeceraDetalle)) {
			query = entityManager.createQuery(sql);
		} else {
			sql = sql + "WHERE ventacabecera_idventa = ?1";
			query = entityManager.createQuery(sql);
			query.setParameter(1, idCabeceraDetalle);
		}
		List<VentaDetalle> list = query.getResultList();
		logger.info("Cantidad de registros encontrados: {}",list);
		return list;		
	}

	@Override
	public Long countVentaCabecera() {
		logger.info("Obteniendo la cantidad de venta cabecera");
		Long total = (long) 0;
		try {
			String sql = "SELECT count(estado) FROM #ENTITY# AS #ENTITY# ";
			sql = sql.replace("#ENTITY#", "VentaCabecera");
			Query query = null;
			sql = sql + " WHERE estado = ?1";
			query = entityManager.createQuery(sql);
			query.setParameter(1, "CONFIRMADO");
			total = (Long) query.getSingleResult();
		} catch (Exception e) {
			total = (long) 0;
		}
		
		return total;
	}

}