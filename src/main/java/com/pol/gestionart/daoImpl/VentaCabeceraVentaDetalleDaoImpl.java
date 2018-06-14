package com.pol.gestionart.daoImpl;

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
	private static final String UNCHECKED = "unchecked";
	
	@Override
	public String getCamposFiltrables() {
		return "";
	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}
	
}