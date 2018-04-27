package com.pol.gestionart.daoImpl;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.FamiliaDao;
import com.pol.gestionart.dao.ProductoDao;
import com.pol.gestionart.entity.Familia;
import com.pol.gestionart.entity.Producto;
@Repository
public class FamiliaDaoImpl extends DaoImpl<Familia> implements FamiliaDao {

	@Override
	public String getCamposFiltrables() {
		// TODO Auto-generated method stub
		return "estado";
	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}

}
