package com.pol.gestionart.daoImpl;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.VentaCabeceraDao;
import com.pol.gestionart.entity.VentaCabecera;

@Repository
public class CompraCabeceraDaoImpl extends DaoImpl<VentaCabecera> implements VentaCabeceraDao{

	@Override
	public String getCamposFiltrables() {
		return "estado";
	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}

}