package com.pol.gestionart.daoImpl;

import com.pol.gestionart.dao.CompraCabeceraDao;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.entity.CompraCabecera;

public class CompraCabeceraDaoImpl extends DaoImpl<CompraCabecera> implements CompraCabeceraDao {

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCamposFiltrables() {
		return "estado";
	}

}
