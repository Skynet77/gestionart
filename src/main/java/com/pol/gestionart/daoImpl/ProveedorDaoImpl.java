package com.pol.gestionart.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.ProveedorDao;
import com.pol.gestionart.entity.Proveedor;

@Repository
public class ProveedorDaoImpl extends DaoImpl<Proveedor> implements ProveedorDao{

	@Override
	public String getCamposFiltrables() {
		return "estado";
	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}

}