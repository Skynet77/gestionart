package com.pol.gestionart.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.CajaDao;
import com.pol.gestionart.entity.Caja;

@Repository
public class CajaDaoImpl extends DaoImpl<Caja> implements CajaDao{

	@Override
	public String getCamposFiltrables() {
		return "fecha||descripcion";
	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}

}