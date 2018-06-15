package com.pol.gestionart.daoImpl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.VentaCabeceraDao;
import com.pol.gestionart.entity.VentaCabecera;
import com.pol.gestionart.entity.VentaDetalle;

@Repository
public class VentaCabeceraDaoImpl extends DaoImpl<VentaCabecera> implements VentaCabeceraDao{

	@Override
	public String getCamposFiltrables() {
		return "estado";
	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<VentaDetalle> getDetalleByIdCab(Long idCabecera) {

		String sql = "SELECT object(#ENTITY#) FROM #ENTITY# AS #ENTITY# ";
		sql = sql.replace("#ENTITY#", "VentaDetalle");
		Query query = null;
		sql = sql + "WHERE ventacabecera_idventa = ?1";
		query = entityManager.createQuery(sql);
		query.setParameter(1, idCabecera);
		List<VentaDetalle> listDetalle = query.getResultList();
		
	
		logger.info("Registros encontrados: {}",listDetalle);
	
		return listDetalle;
	}

}