package com.pol.gestionart.daoImpl;

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

}