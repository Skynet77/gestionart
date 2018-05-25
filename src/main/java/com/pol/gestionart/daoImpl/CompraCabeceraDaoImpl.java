package com.pol.gestionart.daoImpl;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.CompraCabeceraDao;
import com.pol.gestionart.entity.CompraCabecera;

@Repository
public class CompraCabeceraDaoImpl extends DaoImpl<CompraCabecera> implements CompraCabeceraDao{

	@Override
	public String getCamposFiltrables() {
		return "estado";
	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}

}