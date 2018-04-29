package com.pol.gestionart.daoImpl;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.ProductoDao;
import com.pol.gestionart.entity.Producto;
@Repository
public class ProductoDaoImpl extends DaoImpl<Producto> implements ProductoDao {

	@Override
	public String getCamposFiltrables() {
		// TODO Auto-generated method stub
		return "estado||descripcion";
	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}

}
