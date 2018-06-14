package com.pol.gestionart.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.CajaDao;
import com.pol.gestionart.dao.VentaCabeceraVentaDetalleDao;
import com.pol.gestionart.entity.Caja;
import com.pol.gestionart.entity.VentaCabeceraVentaDetalle;

@Repository
public class VentaCabeceraVentaDetalleDaoImpl extends DaoImpl<VentaCabeceraVentaDetalle> implements VentaCabeceraVentaDetalleDao{
	
	@Override
	public String getCamposFiltrables() {
		return "";
	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public VentaCabeceraVentaDetalle getCajaVentaByIdCaja(Long idCaja) {
		
			String sql = "SELECT object(#ENTITY#) FROM #ENTITY# AS #ENTITY# ";
			sql = sql.replace("#ENTITY#", getEntityName());
			Query query = null;
			sql = sql + "WHERE ventacabecera_idventa = ?1";
			query.setParameter(1, idCaja);
			VentaCabeceraVentaDetalle vCabDet = (VentaCabeceraVentaDetalle) query.getSingleResult();
			
		
			logger.info("Registros encontrados: {}",vCabDet);
			return vCabDet;		
		
	}
	
}