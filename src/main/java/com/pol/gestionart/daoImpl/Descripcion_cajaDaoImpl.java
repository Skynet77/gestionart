package com.pol.gestionart.daoImpl;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.Descripcion_cajaDao;
import com.pol.gestionart.entity.DescripcionCaja;
@Repository
public class Descripcion_cajaDaoImpl extends DaoImpl<DescripcionCaja> implements Descripcion_cajaDao {

	@Override
	public String getCamposFiltrables() {
		// TODO Auto-generated method stub
		return "codigo";
	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}

}
